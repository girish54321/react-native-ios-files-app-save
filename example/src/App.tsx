import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import { multiply, startDownload } from 'react-native-ios-files-app-save';

export default function App() {
  console.log({ startDownload });

  const [result, setResult] = React.useState<number | undefined>();

  React.useEffect(() => {
    multiply(3, 7).then(setResult);
  }, []);

  const runPackage = async () => {
    try {
      startDownload("https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf").then((res: any) => {
        console.log(res)
      });
    } catch (e) {
      console.log(e)
    }
  }

  return (
    <View style={styles.container}>
      <Text onPress={runPackage}>{"Start Download"}</Text>
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
