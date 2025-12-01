import React, { useState, useEffect } from 'react';
import axios from 'axios';

interface Idiom {
  id: number;
  content: string;
  pinyin: string;
  meaning: string;
}

const App: React.FC = () => {
  const [idiomChain, setIdiomChain] = useState<Idiom[]>([]);
  const [userInput, setUserInput] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [gameStarted, setGameStarted] = useState(false);

  // Get a random idiom to start the game
  const startGame = async () => {
    setLoading(true);
    setError('');
    try {
      const response = await axios.get<Idiom>('http://localhost:8080/api/idiom/random/start');
      setIdiomChain([response.data]);
      setGameStarted(true);
    } catch (err) {
      setError('无法获取初始成语，请稍后重试');
    } finally {
      setLoading(false);
    }
  };

  // Validate user input and get next idiom from server
  const handleUserInput = async () => {
    if (!userInput.trim()) {
      setError('请输入成语');
      return;
    }

    setLoading(true);
    setError('');

    try {
      // Check if user input is a valid idiom
      const validateResponse = await axios.get<{ valid: boolean }>(
        `http://localhost:8080/api/idiom/validate/${userInput.trim()}`
      );

      if (!validateResponse.data.valid) {
        setError('您输入的不是一个有效的成语');
        return;
      }

      // Check if the first character of user input matches the last character of the last idiom in chain
      const lastIdiom = idiomChain[idiomChain.length - 1];
      const lastCharacter = lastIdiom.content.charAt(lastIdiom.content.length - 1);
      const firstCharacter = userInput.trim().charAt(0);

      if (lastCharacter !== firstCharacter) {
        setError(`您输入的成语第一个字应该是"${lastCharacter}"`);
        return;
      }

      // Add user input to the chain
      const userIdiom: Idiom = {
        id: 0,
        content: userInput.trim(),
        pinyin: '',
        meaning: ''
      };

      setIdiomChain(prev => [...prev, userIdiom]);
      setUserInput('');

      // Get next idiom from server
      const nextResponse = await axios.get<Idiom>(
        `http://localhost:8080/api/idiom/next/${userInput.trim()}`
      );

      setIdiomChain(prev => [...prev, nextResponse.data]);

    } catch (err: any) {
      if (err.response?.status === 404) {
        setError('找不到下一个成语，游戏结束');
      } else {
        setError('发生错误，请稍后重试');
      }
    } finally {
      setLoading(false);
    }
  };

  // Handle keyboard enter event
  const handleKeyPress = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter') {
      handleUserInput();
    }
  };

  return (
    <div style={{ textAlign: 'center', padding: '20px' }}>
      <h1 style={{ color: '#333', marginBottom: '30px' }}>成语接龙</h1>

      {!gameStarted ? (
        <div>
          <button
            onClick={startGame}
            disabled={loading}
            style={{
              padding: '12px 24px',
              fontSize: '16px',
              backgroundColor: '#4CAF50',
              color: 'white',
              border: 'none',
              borderRadius: '4px',
              cursor: loading ? 'not-allowed' : 'pointer'
            }}
          >
            {loading ? '加载中...' : '开始游戏'}
          </button>
        </div>
      ) : (
        <div>
          {/* Idiom Chain Display */}
          <div style={{ marginBottom: '30px', textAlign: 'left' }}>
            <h3 style={{ color: '#555', marginBottom: '15px' }}>接龙过程：</h3>
            <div style={{ display: 'flex', flexWrap: 'wrap', gap: '10px' }}>
              {idiomChain.map((idiom, index) => (
                <div
                  key={index}
                  style={{
                    padding: '8px 16px',
                    backgroundColor: index % 2 === 0 ? '#e3f2fd' : '#fff3e0',
                    border: '1px solid #ccc',
                    borderRadius: '4px',
                    fontSize: '18px',
                    fontWeight: 'bold'
                  }}
                >
                  {idiom.content}
                </div>
              ))}
            </div>
          </div>

          {/* User Input Section */}
          <div style={{ marginBottom: '20px' }}>
            <input
              type="text"
              value={userInput}
              onChange={(e) => setUserInput(e.target.value)}
              onKeyPress={handleKeyPress}
              disabled={loading}
              placeholder="请输入成语"
              style={{
                padding: '10px',
                fontSize: '16px',
                width: '300px',
                border: '1px solid #ccc',
                borderRadius: '4px',
                marginRight: '10px'
              }}
            />
            <button
              onClick={handleUserInput}
              disabled={loading}
              style={{
                padding: '10px 20px',
                fontSize: '16px',
                backgroundColor: '#2196F3',
                color: 'white',
                border: 'none',
                borderRadius: '4px',
                cursor: loading ? 'not-allowed' : 'pointer'
              }}
            >
              {loading ? '思考中...' : '提交'}
            </button>
          </div>

          {/* Error Message */}
          {error && (
            <div style={{ color: '#f44336', marginBottom: '20px', fontSize: '16px' }}>
              {error}
            </div>
          )}

          {/* Start New Game Button */}
          <button
            onClick={() => {
              setGameStarted(false);
              setIdiomChain([]);
              setUserInput('');
              setError('');
            }}
            style={{
              padding: '10px 20px',
              fontSize: '16px',
              backgroundColor: '#FF9800',
              color: 'white',
              border: 'none',
              borderRadius: '4px',
              cursor: 'pointer'
            }}
          >
            重新开始
          </button>
        </div>
      )}
    </div>
  );
};

export default App;