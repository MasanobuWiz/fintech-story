const functions = require('firebase-functions');
const admin = require('firebase-admin');

// Firebase Admin SDKの初期化
admin.initializeApp({
  credential: admin.credential.applicationDefault(),
  databaseURL: 'https://fintech-bd8e7-default-rtdb.firebaseio.com/'
});

// Cloud Functionの定義
exports.getComics = functions.https.onRequest((req, res) => {
  const ref = admin.database().ref('/comics');
  ref.once('value', (snapshot) => {
    res.status(200).send(snapshot.val());
  }).catch((error) => {
    res.status(500).send(error);
  });
});
