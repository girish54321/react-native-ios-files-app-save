import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import { stateDownloadAppSave } from 'react-native-ios-files-app-save';

export default function App() {

  const demoDownload = () => {
    stateDownloadAppSave("https://unec.edu.az/application/uploads/2014/12/pdf-sample.pdf").then((res: any) => {
      console.log(res);
    }).catch((e) => {
      console.log("error", e);
    })
  }

  return (
    <View style={styles.container}>
      <Text onPress={demoDownload}>{"Start Download"}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
