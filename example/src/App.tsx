import * as React from 'react';

import { StyleSheet, View, Text, FlatList, TouchableOpacity, Image, SafeAreaView } from 'react-native';
import { FileSaveSuccess, stateDownloadAppSave } from 'react-native-ios-files-app-save';

const fileArray = [
  {
    title: "PDF",
    image: require('./images/pdf.png'),
    url: "https://sample-videos.com/pdf/Sample-pdf-5mb.pdf"
  },
  {
    title: "PNG",
    image: require('./images/png.png'),
    url: "https://sample-videos.com/img/Sample-png-image-1mb.png"
  },
  {
    title: "ZIP",
    image: require('./images/zip.png'),
    url: "https://sample-videos.com/zip/10mb.zip"
  },
  {
    title: "JPG",
    image: require('./images/jpg.png'),
    url: "https://sample-videos.com/img/Sample-jpg-image-1mb.jpg"
  },
  {
    title: "DOC",
    image: require('./images/doc.png'),
    url: "https://sample-videos.com/doc/Sample-doc-file-1000kb.doc"
  }
]
export default function App() {


  const renderItem = ({ item }) => {
    return (
      <View style={{ flex: 0.5, padding: 22, }} >

        <TouchableOpacity onPress={() => {
          demoDownload(item.url);
        }} >
          <Image source={item.image} style={{ height: 160, width: 160, padding: 22 }} />
        </TouchableOpacity>
        <Text style={{ alignSelf: 'center', marginTop: 16, fontSize: 22 }}>{item.title}</Text>
      </View>
    );
  };

  const demoDownload = (url: string) => {
    stateDownloadAppSave("url", "nudes.zip").then((res) => {
      const fileSaveSuccess = res as FileSaveSuccess;
      console.log(fileSaveSuccess);
      console.log(fileSaveSuccess.message);
    }).catch((error) => {
      console.log("error", error);
    })
  }



  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.container}>
        <FlatList
          data={fileArray}
          renderItem={renderItem}
          numColumns={2}
        />
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
