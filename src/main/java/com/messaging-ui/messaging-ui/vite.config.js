import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'

export default defineConfig({
  plugins: [react(), tailwindcss()],
  server: {
    proxy: {
      // Sends /messaging/... requests to GlassFish, so no CORS errors
      '/messaging': 'http://localhost:8080',
    },
  },
})