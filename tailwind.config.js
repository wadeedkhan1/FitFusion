/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/main/resources/templates/**/*.html", "./src/main/resources/static/js/**/*.js"],
  theme: {
    extend: {
      colors: {
        primary: '#2563eb', // Example premium blue
        secondary: '#1e40af',
        accent: '#f59e0b',
      },
      fontFamily: {
        sans: ['Inter', 'sans-serif'],
      },
    },
  },
  plugins: [],
}
