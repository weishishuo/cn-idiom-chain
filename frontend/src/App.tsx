import { useState, useEffect, useRef } from 'react';
import { Message, GameState } from './types/message';
import { idiomApi, GetNextIdiomResponse } from './services/api';
import './index.css';

const App = () => {
  const [gameState, setGameState] = useState<GameState>({
    messages: [],
    previousIdiom: null,
    isLoading: false,
    error: null,
  });
  const [userInput, setUserInput] = useState('');
  const messagesEndRef = useRef<HTMLDivElement>(null);

  // 滚动到最新消息
  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  useEffect(() => {
    scrollToBottom();
  }, [gameState.messages]);

  // 初始化欢迎消息
  useEffect(() => {
    const welcomeMessage: Message = {
      id: Date.now().toString(),
      type: 'system',
      content: '欢迎玩成语接龙游戏，请输入一个四字成语',
      timestamp: new Date(),
    };
    setGameState(prev => ({ ...prev, messages: [welcomeMessage] }));
  }, []);

  // 发送消息
  const handleSendMessage = async () => {
    const input = userInput.trim();
    if (!input || input.length !== 4) {
      setGameState(prev => ({ ...prev, error: '请输入四字成语' }));
      return;
    }

    // 清除错误
    setGameState(prev => ({ ...prev, error: null }));

    // 添加用户消息
    const userMessage: Message = {
      id: Date.now().toString(),
      type: 'user',
      content: input,
      timestamp: new Date(),
    };

    setGameState(prev => ({
      ...prev,
      messages: [...prev.messages, userMessage],
      isLoading: true,
    }));

    // 清空输入框
    setUserInput('');

    try {
      // 调用后端API
      const response: GetNextIdiomResponse = await idiomApi.getNextIdiom({
        userInput: input,
        previousIdiom: gameState.previousIdiom || undefined,
      });

      if (response.success && response.idiom) {
        // 成功获取接龙成语
        const systemMessage: Message = {
          id: (Date.now() + 1).toString(),
          type: 'system',
          content: response.idiom,
          timestamp: new Date(),
          meaning: response.meaning,
        };

        setGameState(prev => ({
          ...prev,
          messages: [...prev.messages, systemMessage],
          previousIdiom: response.idiom,
          isLoading: false,
        }));
      } else {
        // 处理错误情况
        const errorMessage: Message = {
          id: (Date.now() + 1).toString(),
          type: 'system',
          content: response.message || '未知错误',
          timestamp: new Date(),
        };

        setGameState(prev => ({
          ...prev,
          messages: [...prev.messages, errorMessage],
          isLoading: false,
        }));
      }
    } catch (error) {
      console.error('API调用失败:', error);
      const errorMessage: Message = {
        id: (Date.now() + 1).toString(),
        type: 'system',
        content: '网络错误，请稍后重试',
        timestamp: new Date(),
      };

      setGameState(prev => ({
        ...prev,
        messages: [...prev.messages, errorMessage],
        isLoading: false,
      }));
    }
  };

  // 重新开始游戏
  const handleRestart = () => {
    const welcomeMessage: Message = {
      id: Date.now().toString(),
      type: 'system',
      content: '欢迎玩成语接龙游戏，请输入一个四字成语',
      timestamp: new Date(),
    };
    setGameState({
      messages: [welcomeMessage],
      previousIdiom: null,
      isLoading: false,
      error: null,
    });
    setUserInput('');
  };

  // 处理回车键
  const handleKeyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      handleSendMessage();
    }
  };

  return (
    <div className="app-container">
      <div className="header">
        <h1>成语接龙</h1>
      </div>

      <div className="messages-container">
        {gameState.messages.map(message => (
          <div key={message.id} className={`message ${message.type}`}>
            <div className="message-content">
              <div>{message.content}</div>
              {message.meaning && (
                <div className="message-info">{message.meaning}</div>
              )}
            </div>
          </div>
        ))}
        {gameState.isLoading && (
          <div className="message system">
            <div className="message-content">
              <div className="loading">思考中...</div>
            </div>
          </div>
        )}
        <div ref={messagesEndRef} />
      </div>

      <div className="input-container">
        {gameState.error && <div className="error">{gameState.error}</div>}
        <div className="input-wrapper">
          <input
            type="text"
            value={userInput}
            onChange={(e) => setUserInput(e.target.value)}
            onKeyPress={handleKeyPress}
            placeholder="请输入四字成语"
            maxLength={4}
            disabled={gameState.isLoading}
          />
          <button onClick={handleSendMessage} disabled={gameState.isLoading}>
            发送
          </button>
        </div>
        <button className="restart-button" onClick={handleRestart}>
          重新开始
        </button>
      </div>
    </div>
  );
};

export default App;