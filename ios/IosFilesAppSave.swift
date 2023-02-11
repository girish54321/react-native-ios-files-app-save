@objc(IosFilesAppSave)
class IosFilesAppSave: NSObject {

  @objc
  func startDownload(_ string: String,  resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
    let fileURL : String = string;
   
    guard let url = URL(string: fileURL) else {
      reject("Error","Invalid URL",nil)
      return
    }
      let fileName = url.lastPathComponent
      let pdfData = try? Data.init(contentsOf: url)
      let resDocPath = (FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).last!) as URL
      let pdfFileName = fileName
      let filePath = resDocPath.appendingPathComponent(pdfFileName ?? "file")
      do {
        try pdfData?.write(to: filePath,options: .atomic)
        resolve("File Saved")
      } catch {
        reject("Error","Invalid URL",nil)
      }
  }

  @objc(multiply:withB:withResolver:withRejecter:)
  func multiply(a: Float, b: Float, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) -> Void {
    resolve(a*b)
  }
}
