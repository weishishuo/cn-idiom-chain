interface Message {
    content: string;
    isUser: boolean;
}

class IdiomChainGame {
    private chatMessages: HTMLElement;
    private userInput: HTMLInputElement;
    private sendBtn: HTMLButtonElement;
    private restartBtn: HTMLButtonElement;
    private previousIdiom: string | null = null;
    private messages: Message[] = [];

    constructor() {
        this.chatMessages = document.getElementById('chatMessages') as HTMLElement;
        this.userInput = document.getElementById('userInput') as HTMLInputElement;
        this.sendBtn = document.getElementById('sendBtn') as HTMLButtonElement;
        this.restartBtn = document.getElementById('restartBtn') as HTMLButtonElement;

        this.initializeEventListeners();
    }

    private initializeEventListeners(): void {
        this.sendBtn.addEventListener('click', () => this.handleSend());
        this.userInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                this.handleSend();
            }
        });
        this.restartBtn.addEventListener('click', () => this.handleRestart());
    }

    private async handleSend(): void {
        const inputValue = this.userInput.value.trim();
        
        if (!inputValue) {
            return;
        }

        // 添加用户消息
        this.addMessage(inputValue, true);
        this.userInput.value = '';

        try {
            const response = await fetch('http://localhost:8081/api/idiom/next', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    userInput: inputValue,
                    previousIdiom: this.previousIdiom
                }),
            });

            const data = await response.json();

            if (data.success) {
                this.previousIdiom = data.idiom;
                this.addMessage(data.idiom, false);
            } else {
                this.addMessage(data.message, false);
            }
        } catch (error) {
            console.error('Error:', error);
            this.addMessage('网络错误，请稍后重试', false);
        }
    }

    private handleRestart(): void {
        this.previousIdiom = null;
        this.messages = [];
        this.chatMessages.innerHTML = '';
        this.addMessage('欢迎玩成语接龙游戏，请输入一个四字成语', false);
        this.userInput.value = '';
        this.userInput.focus();
    }

    private addMessage(content: string, isUser: boolean): void {
        const message: Message = { content, isUser };
        this.messages.push(message);

        const messageDiv = document.createElement('div');
        messageDiv.className = `message ${isUser ? 'user' : 'system'}`;

        const contentDiv = document.createElement('div');
        contentDiv.className = 'message-content';

        const p = document.createElement('p');
        p.textContent = content;

        contentDiv.appendChild(p);
        messageDiv.appendChild(contentDiv);
        this.chatMessages.appendChild(messageDiv);

        // 滚动到底部
        this.chatMessages.scrollTop = this.chatMessages.scrollHeight;
    }
}

// 初始化游戏
window.addEventListener('DOMContentLoaded', () => {
    new IdiomChainGame();
});