export type MessageType = 'user' | 'system';

export interface Message {
  id: string;
  type: MessageType;
  content: string;
  timestamp: Date;
  meaning?: string;
}

export interface GameState {
  messages: Message[];
  previousIdiom: string | null;
  isLoading: boolean;
  error: string | null;
}