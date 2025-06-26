import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8081', 
});


const authHeader = localStorage.getItem('authHeader');
if (authHeader) {
  api.defaults.headers.common['Authorization'] = authHeader;
}

export default api;
