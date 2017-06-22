'use strict';

const functions = require('firebase-functions');

var admin = require("firebase-admin");
admin.initializeApp({
  credential: admin.credential.cert({
    projectId:  "",
    clientEmail: "",
    privateKey: "-----BEGIN PRIVATE KEY-----\nKEY\n-----END PRIVATE KEY-----\n"
  }),
  databaseURL: ""
});

exports.storage2database = functions.storage.object().onChange(event => {    
    const object = event.data; // The Storage object.

    const imageFilePath = object.name; // File path in the bucket.

    var db = admin.database();
    var reportRef = db.ref("storage_reports");
    var newImageReportRef = reportRef.push();
    newImageReportRef.set({
        image: imageFilePath
    });
});