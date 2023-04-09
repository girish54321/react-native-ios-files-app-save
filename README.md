# react-native-ios-files-app-save

react-native-ios-files-app-save is a React Native package that allows you to easily download any file from the internet and save it to your Files app on Android and iOS devices.

![App Screenshot](https://raw.githubusercontent.com/girish54321/DownloadApp/master/appimage.png)

## Installation

To install react-native-ios-files-app-save, use npm or yarn

### npm

```sh
npm install react-native-ios-files-app-save
```

### yarn

```sh
yarn add react-native-ios-files-app-save
```

### Android AndroidManifest.xml

```sh
<uses-permission android:name="android.permission.DOWNLOAD_WITHOUT_NOTIFICATION" />
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" tools:ignore="ScopedStorage" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

### iOS info.plist

```sh
<key>LSSupportsOpeningDocumentsInPlace</key>
  <true/>
<key>UIFileSharingEnabled</key>
  <true/>
```

## Usage

To use react-native-ios-files-app-save simply import the `startDownloadAppSave` function and call it with the URL of the file you want to download:

```js
import { startDownloadAppSave } from 'react-native-ios-files-app-save';

export default function App() {
  const demoDownload = () => {
    if (!hasPermission) {
      //* Handle Permission
      return;
    }
   let options: FileSaveOptions = {
      url: "YOUR URL,
      fileName: "name.pdf,
      isBase64: false
    }
    startDownloadAppSave(options).then((res) => {
      const fileSaveSuccess = res as FileSaveSuccess;
      console.log(fileSaveSuccess);
      console.log(fileSaveSuccess.message);
    }).catch((error) => {
      console.log("error", error);
    })
  };

  return (
    <View style={styles.container}>
      <Text onPress={demoDownload}>{'Start Download'}</Text>
    </View>
  );
}
```

## Contributing

Contributions are welcome! If you have any issues, ideas, or suggestions for react-native-ios-files-app-save, feel free to open an issue or submit a pull request on Github.

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
