import axios from 'axios';

const API_BASE_URL = 'http://localhost:8081/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export interface GetNextIdiomRequest {
  userInput: string;
  previousIdiom?: string;
}

export interface GetNextIdiomResponse {
  success: boolean;
  idiom?: string;
  meaning?: string;
  message?: string;
}

export const idiomApi = {
  getNextIdiom: async (request: GetNextIdiomRequest): Promise<GetNextIdiomResponse> => {
    const response = await api.post('/idiom/next', request);
    return response.data;
  },
};

export default api;