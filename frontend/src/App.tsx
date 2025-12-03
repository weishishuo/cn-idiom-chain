import React, { useState, useEffect, useRef } from 'react'
import axios from 'axios'

interface Message {
  id: number
  content: string
  type: 'user' | 'system'
}

const App: React.FC = () => {
  const [messages, setMessages] = useState<Message[]>([])
  const [inputValue, setInputValue] = useState('')
  const [previousIdiom, setPreviousIdiom] = useState<string | null>(null)
  const [isLoading, setIsLoading] = useState(false)
  const messagesEndRef = useRef<HTMLDivElement>(null)

  // 自动滚动到底部
  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' })
  }, [messages])

  // 初始化欢迎消息
  useEffect(() => {
    const welcomeMessage: Message = {
      id: 1,
      content: '欢迎玩成语接龙游戏，请输入一个四字成语',
      type: 'system'
    }
    setMessages([welcomeMessage])
  }, [])

  // 处理用户输入
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(e.target.value)
  }

  // 发送消息
  const sendMessage = async () => {
    if (inputValue.trim().length === 0) return
    if (inputValue.trim().length !== 4) {
      alert('请输入四字成语')
      return
    }

    // 添加用户消息
    const userMessage: Message = {
      id: messages.length + 1,
      content: inputValue.trim(),
      type: 'user'
    }
    setMessages([...messages, userMessage])
    setInputValue('')
    setIsLoading(true)

    try {
      // 调用后端API
      const response = await axios.post('http://localhost:8081/api/idiom/chain', {
        userInput: userMessage.content,
        previousIdiom: previousIdiom
      })

      if (response.data.status === 'success') {
        // 添加系统回复
        const systemMessage: Message = {
          id: messages.length + 2,
          content: response.data.nextIdiom,
          type: 'system'
        }
        setMessages([...messages, userMessage, systemMessage])
        setPreviousIdiom(response.data.nextIdiom)
      } else {
        // 添加错误消息
        const errorMessage: Message = {
          id: messages.length + 2,
          content: response.data.message,
          type: 'system'
        }
        setMessages([...messages, userMessage, errorMessage])
      }
    } catch (error) {
      console.error('请求失败:', error)
      let errorContent = '网络错误，请稍后重试'

      // 检查是否是Axios错误
      if (axios.isAxiosError(error)) {
        // 检查是否有响应数据
        if (error.response) {
          // 从响应数据中获取错误信息
          if (error.response.data && error.response.data.message) {
            errorContent = error.response.data.message
          } else if (error.response.status === 404) {
            errorContent = 'API接口不存在，请检查后端服务是否正确启动'
          } else {
            errorContent = `请求失败 (${error.response.status}): ${error.response.statusText}`
          }
        } else if (error.request) {
          // 请求已发送但未收到响应
          errorContent = '服务器无响应，请检查后端服务是否运行'
        }
      }

      const errorMessage: Message = {
        id: messages.length + 2,
        content: errorContent,
        type: 'system'
      }
      setMessages([...messages, userMessage, errorMessage])
    } finally {
      setIsLoading(false)
    }
  }

  // 处理回车键
  const handleKeyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      sendMessage()
    }
  }

  // 重新开始游戏
  const restartGame = () => {
    setMessages([])
    setPreviousIdiom(null)
    setInputValue('')
    // 重新初始化欢迎消息
    const welcomeMessage: Message = {
      id: 1,
      content: '欢迎玩成语接龙游戏，请输入一个四字成语',
      type: 'system'
    }
    setMessages([welcomeMessage])
  }

  return (
    <div style={{
      width: '100%',
      maxWidth: '600px',
      height: '80vh',
      maxHeight: '600px',
      backgroundColor: '#fff',
      borderRadius: '20px',
      boxShadow: '0 0 20px rgba(0, 0, 0, 0.1)',
      display: 'flex',
      flexDirection: 'column',
      overflow: 'hidden'
    }}>
      {/* 消息列表 */}
      <div style={{
        flex: 1,
        overflowY: 'auto',
        padding: '20px',
        display: 'flex',
        flexDirection: 'column'
      }}>
        {messages.map((message) => (
          <div
            key={message.id}
            style={{
              alignSelf: message.type === 'user' ? 'flex-end' : 'flex-start',
              maxWidth: '70%',
              padding: '10px 15px',
              borderRadius: message.type === 'user' ? '15px 15px 0 15px' : '15px 15px 15px 0',
              backgroundColor: message.type === 'user' ? '#0084ff' : '#f0f0f0',
              color: message.type === 'user' ? '#fff' : '#333',
              marginBottom: '10px',
              wordWrap: 'break-word'
            }}
          >
            {message.content}
          </div>
        ))}
        {isLoading && (
          <div
            style={{
              alignSelf: 'flex-start',
              maxWidth: '70%',
              padding: '10px 15px',
              borderRadius: '15px 15px 15px 0',
              backgroundColor: '#f0f0f0',
              color: '#333',
              marginBottom: '10px'
            }}
          >
            思考中...
          </div>
        )}
        <div ref={messagesEndRef} />
      </div>

      {/* 输入区域 */}
      <div style={{
        padding: '15px 20px',
        borderTop: '1px solid #f0f0f0',
        display: 'flex',
        flexDirection: 'column',
        gap: '10px'
      }}>
        <div style={{
          display: 'flex',
          gap: '10px'
        }}>
          <input
            type="text"
            value={inputValue}
            onChange={handleInputChange}
            onKeyPress={handleKeyPress}
            placeholder="请输入四字成语"
            disabled={isLoading}
            style={{
              flex: 1,
              padding: '10px 15px',
              borderRadius: '20px',
              border: '1px solid #e0e0e0',
              outline: 'none',
              fontSize: '14px',
              backgroundColor: isLoading ? '#f0f0f0' : '#fff'
            }}
          />
          <button
            onClick={sendMessage}
            disabled={isLoading || inputValue.trim().length === 0 || inputValue.trim().length !== 4}
            style={{
              padding: '10px 20px',
              borderRadius: '20px',
              border: 'none',
              backgroundColor: '#0084ff',
              color: '#fff',
              fontSize: '14px',
              cursor: 'pointer',
              opacity: isLoading || inputValue.trim().length === 0 || inputValue.trim().length !== 4 ? 0.5 : 1
            }}
          >
            发送
          </button>
        </div>

        {/* 重新开始按钮 */}
        <button
          onClick={restartGame}
          style={{
            padding: '10px 20px',
            borderRadius: '20px',
            border: '1px solid #0084ff',
            backgroundColor: '#fff',
            color: '#0084ff',
            fontSize: '14px',
            cursor: 'pointer',
            alignSelf: 'center'
          }}
        >
          重新开始
        </button>
      </div>
    </div>
  )
}

export default App
