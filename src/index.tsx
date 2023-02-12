import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-ios-files-app-save' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const IosFilesAppSave = NativeModules.IosFilesAppSave
  ? NativeModules.IosFilesAppSave
  : new Proxy(
    {},
    {
      get() {
        throw new Error(LINKING_ERROR);
      },
    }
  );



export function multiply(a: number, b: number): Promise<number> {
  return IosFilesAppSave.multiply(a, b);
}


export const startDownload = async (url: string) => {
  try {
    NativeModules.IosFilesAppSave.startDownload(url).then(() => {
    });
  } catch (e) {
  }
}

export const stateDownloadAppSave = (url: string) => {
  return new Promise((resolve, reject) => {
    try {
      NativeModules.IosFilesAppSave.startDownload(url).then((res: any) => {
        resolve(res)
      }).catch((e: any) => {
        console.log("this in index");
        console.log("e2", e);
        reject(e)
      });
    } catch (e) {
    }
  });
}