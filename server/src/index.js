const app = require('./app');

const port = Number(process.env.PORT || 8080);

app.listen(port, () => {
  console.log(`balance-fit-server listening on ${port}`);
});
