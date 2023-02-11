import * as React from 'react';

import { StyleSheet, View, Text, FlatList, TouchableOpacity, Image, SafeAreaView } from 'react-native';
import { stateDownloadAppSave } from 'react-native-ios-files-app-save';

const fileArray = [
  {
    title: "PDF",
    image: require('./images/pdf.png'),
    url: "http://www.africau.edu/images/default/sample.pdf"
  },
  {
    title: "PNG",
    image: require('./images/png.png'),
    url: "https://file-examples-com.github.io/uploads/2017/10/file_example_PNG_1MB.png"
  },
  {
    title: "ZIP",
    image: require('./images/zip.png'),
    url: "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-large-zip-file.zip"
  },
  {
    title: "JPG",
    image: require('./images/jpg.png'),
    url: "https://file-examples-com.github.io/uploads/2017/10/file_example_JPG_1MB.jpg"
  },
  {
    title: "DOC",
    image: require('./images/doc.png'),
    url: "https://file-examples-com.github.io/uploads/2017/02/file-sample_1MB.doc"
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
    stateDownloadAppSave(url).then((res: any) => {
      console.log(res);
    }).catch((e) => {
      console.log("error", e);
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
