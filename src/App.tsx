import { useState } from 'react'

interface HistoryItem {
  type: 'user' | 'system'
  idiom: string
  pinyin?: string
}

interface ApiResponse {
  idiom?: string
  pinyin?: string
  error?: string
}

function App() {
  const [inputIdiom, setInputIdiom] = useState('')
  const [history, setHistory] = useState<HistoryItem[]>([])
  const [message, setMessage] = useState<{ text: string; type: 'success' | 'error' | 'info' } | null>(null)
  const [loading, setLoading] = useState(false)

  const clearMessage = () => {
    setMessage(null)
  }

  const showMessage = (text: string, type: 'success' | 'error' | 'info') => {
    setMessage({ text, type })
    setTimeout(clearMessage, 5000)
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    
    if (!inputIdiom.trim()) {
      showMessage('请输入成语', 'error')
      return
    }

    // 验证格式：2-8个汉字
    const chineseRegex = /^[\u4e00-\u9fa5]{2,8}$/
    if (!chineseRegex.test(inputIdiom.trim())) {
      showMessage('请输入有效的中文成语（2-8个汉字）', 'error')
      return
    }

    const userIdiom = inputIdiom.trim()

    // 检查接龙是否匹配
    if (history.length > 0) {
      const lastSystemItem = history[history.length - 1]
      if (lastSystemItem.type === 'system') {
        const lastChar = lastSystemItem.idiom[lastSystemItem.idiom.length - 1]
        const firstChar = userIdiom[0]
        if (lastChar !== firstChar) {
          showMessage(`需要以"${lastChar}"开头的成语`, 'error')
          return
        }
      }
    }

    setLoading(true)

    try {
      // 先验证用户输入的成语是否存在
      const validateResponse = await fetch('http://localhost:8080/api/idiom/validate', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ idiom: userIdiom }),
      })

      const validateResult = await validateResponse.json()

      if (!validateResult.valid) {
        showMessage('你输入的不是有效的成语，请重新输入', 'error')
        return
      }

      // 添加用户输入到历史记录
      setHistory(prev => [...prev, { type: 'user', idiom: userIdiom }])
      setInputIdiom('')

      // 请求下一个接龙成语
      const nextResponse = await fetch('http://localhost:8080/api/idiom/next', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ lastIdiom: userIdiom }),
      })

      const nextResult: ApiResponse = await nextResponse.json()

      if (nextResult.error) {
        showMessage(nextResult.error, 'error')
        return
      }

      if (nextResult.idiom) {
        setHistory(prev => [...prev, { type: 'system', idiom: nextResult.idiom, pinyin: nextResult.pinyin }])
        showMessage(`我说：${nextResult.idiom}（${nextResult.pinyin}）`, 'success')
      }
    } catch (error) {
      showMessage('网络错误，请稍后重试', 'error')
      console.error('Error:', error)
    } finally {
      setLoading(false)
    }
  }

  const resetGame = () => {
    setHistory([])
    setInputIdiom('')
    setMessage(null)
    showMessage('游戏已重置，请开始第一个成语', 'info')
  }

  return (
    <div className="app-container">
      <h1 className="title">成语接龙</h1>
      
      {message && (
        <div className={`message message-${message.type}`}>
          {message.text}
        </div>
      )}

      <div className="history-container">
        <h2>接龙历史</h2>
        <div className="history-list">
          {history.length === 0 ? (
            <div className="empty-history">点击下方输入框开始游戏</div>
          ) : (
            history.map((item, index) => (
              <div key={index} className={`history-item ${item.type}`}>
                <span className="item-type">{item.type === 'user' ? '你说：' : '我说：'}</span>
                <span className="item-idiom">{item.idiom}</span>
                {item.pinyin && <span className="item-pinyin">（{item.pinyin}）</span>}
              </div>
            ))
          )}
        </div>
      </div>

      <form onSubmit={handleSubmit} className="input-form">
        <input
          type="text"
          value={inputIdiom}
          onChange={(e) => setInputIdiom(e.target.value)}
          placeholder="请输入成语"
          disabled={loading}
          className="idiom-input"
        />
        <button type="submit" disabled={loading} className="submit-btn">
          {loading ? '思考中...' : '接龙'}
        </button>
        <button type="button" onClick={resetGame} className="reset-btn">
          重置游戏
        </button>
      </form>
    </div>
  )
}

export default App