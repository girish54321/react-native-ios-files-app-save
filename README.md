# react-native-ios-files-app-save

file dowload

## Installation

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
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
        tools:ignore="ScopedStorage" />
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

```js
import { stateDownloadAppSave } from 'react-native-ios-files-app-save';

export default function App() {
  const demoDownload = () => {
    if (!hasPermission) {
      //* Handle Permission
      return;
    }
    stateDownloadAppSave('https://YOUFDF.COM/FILE/BILL/OCT.pdf')
      .then((res: any) => {
        console.log(res);
      })
      .catch((e) => {
        console.log('error', e);
      });
  };

  return (
    <View style={styles.container}>
      <Text onPress={demoDownload}>{'Start Download'}</Text>
    </View>
  );
}
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
