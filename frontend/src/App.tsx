import React, { useState, useEffect } from 'react'
import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080/api';

interface Idiom {
  id: number
  content: string
  pinyin: string
  meaning: string
  first_char: string
  last_char: string
}

interface ApiResponse<T> {
  code: number
  message: string
  data: T
}

const App: React.FC = () => {
  const [idiomChain, setIdiomChain] = useState<Idiom[]>([])
  const [userInput, setUserInput] = useState('')
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState('')
  const [gameOver, setGameOver] = useState(false)

  const handleUserInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUserInput(e.target.value)
  }

  const validateUserInput = () => {
    if (!userInput.trim()) {
      setError('请输入成语')
      return false
    }

    if (userInput.length !== 4) {
      setError('成语必须是4个字')
      return false
    }

    if (idiomChain.length > 0) {
      const lastIdiom = idiomChain[idiomChain.length - 1]
      if (userInput[0] !== lastIdiom.last_char) {
      setError(`成语的第一个字必须是"${lastIdiom.last_char}"`)
      return false
    }
    }

    setError('')
    return true
  }

  const getUserInputIdiom = async () => {
    try {
      const response = await axios.get<ApiResponse<Idiom>>(
        `${API_BASE_URL}/idiom-chain/validate?idiom=${encodeURIComponent(userInput)}`
      )
      return response.data.data
    } catch (err: any) {
      if (err.response?.data?.message) {
        setError(err.response.data.message)
      } else {
        setError('验证成语失败')
      }
      throw err
    }
  }

  const getNextIdiom = async (currentIdiom: string) => {
    try {
      const response = await axios.get<ApiResponse<Idiom>>(
        `${API_BASE_URL}/idiom-chain/next?currentIdiom=${encodeURIComponent(currentIdiom)}`
      )
      return response.data.data
    } catch (err: any) {
      if (err.response?.data?.message) {
        setError(err.response.data.message)
      } else {
        setError('获取下一个成语失败')
      }
      throw err
    }
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()

    if (!validateUserInput()) {
      return
    }

    setIsLoading(true)
    setError('')

    try {
      // 验证用户输入的成语是否存在
      const userIdiom = await getUserInputIdiom()

      // 添加用户输入的成语到接龙列表
      const newChain = [...idiomChain, userIdiom]
      setIdiomChain(newChain)

      // 获取下一个成语
      const nextIdiom = await getNextIdiom(userIdiom.content)

      // 添加下一个成语到接龙列表
      setIdiomChain([...newChain, nextIdiom])

      // 清空用户输入
      setUserInput('')
    } catch (err) {
      // 如果获取下一个成语失败，游戏结束
      setGameOver(true)
    } finally {
      setIsLoading(false)
    }
  }

  const handleRestart = () => {
    setIdiomChain([])
    setUserInput('')
    setError('')
    setGameOver(false)
  }

  return (
    <div style={styles.container}>
      <h1 style={styles.title}>成语接龙游戏</h1>

      {error && <div style={styles.error}>{error}</div>}

      {gameOver && (
        <div style={styles.gameOver}>
          <h2>游戏结束</h2>
          <p>成语库中找不到下一个成语了</p>
          <button style={styles.restartButton} onClick={handleRestart}>
            重新开始
          </button>
        </div>
      )}

      {idiomChain.length > 0 && (
        <div style={styles.chainContainer}>
          <h2 style={styles.chainTitle}>接龙过程</h2>
          <div style={styles.chain}>
            {idiomChain.map((idiom, index) => (
              <div key={idiom.id} style={styles.idiomItem}>
                <span style={styles.idiomContent}>{idiom.content}</span>
                <span style={styles.idiomIndex}>{index + 1}</span>
              </div>
            ))}
          </div>
        </div>
      )}

      {!gameOver && (
        <form style={styles.form} onSubmit={handleSubmit}>
          <div style={styles.inputContainer}>
            <label style={styles.label} htmlFor="idiomInput">
              {idiomChain.length > 0
                ? `请输入以"${idiomChain[idiomChain.length - 1].last_char}"开头的成语`
                : '请输入第一个成语开始游戏'}
            </label>
            <input
              id="idiomInput"
              type="text"
              value={userInput}
              onChange={handleUserInput}
              style={styles.input}
              placeholder="请输入成语"
              disabled={isLoading}
            />
          </div>
          <button type="submit" style={styles.submitButton} disabled={isLoading}>
            {isLoading ? '处理中...' : '提交'}
          </button>
        </form>
      )}
    </div>
  )
}

const styles: { [key: string]: React.CSSProperties } = {
  container: {
    maxWidth: 800,
    margin: '0 auto',
    padding: '20px',
    backgroundColor: '#fff',
    borderRadius: '8px',
    boxShadow: '0 2px 8px rgba(0, 0, 0, 0.1)',
    minHeight: '100vh',
  },
  title: {
    textAlign: 'center',
    marginBottom: '30px',
    color: '#333',
    fontSize: '2.5rem',
  },
  error: {
    backgroundColor: '#fee',
    color: '#c33',
    padding: '12px',
    borderRadius: '4px',
    marginBottom: '20px',
    border: '1px solid #fcc',
  },
  gameOver: {
    textAlign: 'center',
    padding: '40px',
    backgroundColor: '#f9f9f9',
    borderRadius: '8px',
    marginBottom: '20px',
  },
  restartButton: {
    backgroundColor: '#4285f4',
    color: '#fff',
    border: 'none',
    padding: '12px 24px',
    borderRadius: '4px',
    fontSize: '1rem',
    cursor: 'pointer',
    marginTop: '20px',
  },
  chainContainer: {
    marginBottom: '30px',
  },
  chainTitle: {
    fontSize: '1.5rem',
    marginBottom: '15px',
    color: '#333',
  },
  chain: {
    display: 'flex',
    flexWrap: 'wrap',
    gap: '10px',
    padding: '20px',
    backgroundColor: '#f9f9f9',
    borderRadius: '8px',
  },
  idiomItem: {
    position: 'relative',
    backgroundColor: '#4285f4',
    color: '#fff',
    padding: '12px 16px',
    borderRadius: '4px',
    fontSize: '1.1rem',
    fontWeight: 'bold',
    boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
  },
  idiomContent: {
    marginRight: '20px',
  },
  idiomIndex: {
    position: 'absolute',
    top: '-8px',
    right: '-8px',
    backgroundColor: '#ff5722',
    color: '#fff',
    width: '24px',
    height: '24px',
    borderRadius: '50%',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    fontSize: '0.8rem',
  },
  form: {
    display: 'flex',
    flexDirection: 'column',
    gap: '20px',
  },
  inputContainer: {
    display: 'flex',
    flexDirection: 'column',
    gap: '8px',
  },
  label: {
    fontSize: '1.1rem',
    color: '#333',
    fontWeight: '500',
  },
  input: {
    padding: '12px',
    fontSize: '1rem',
    border: '1px solid #ddd',
    borderRadius: '4px',
    outline: 'none',
  },
  submitButton: {
    backgroundColor: '#4285f4',
    color: '#fff',
    border: 'none',
    padding: '12px 24px',
    borderRadius: '4px',
    fontSize: '1rem',
    cursor: 'pointer',
    alignSelf: 'flex-start',
  },
}

export default App