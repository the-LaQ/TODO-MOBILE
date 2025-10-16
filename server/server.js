const express = require('express');
const cors = require('cors');
const fs = require('fs');

const app = express();
const PORT = 3000;

app.use(cors());


app.get('/orders', (req, res) => {
  fs.readFile('orders.json', 'utf8', (err, data) => {
    if (err) {
      console.error('Ошибка при чтении файла:', err);
      res.status(500).json({ error: 'Ошибка сервера' });
      return;
    }

    res.json(JSON.parse(data));
  });
});

app.listen(PORT, '0.0.0.0', () => {
  console.log(`http://localhost:${PORT}`);
});
