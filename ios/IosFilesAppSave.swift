@objc(IosFilesAppSave)
class IosFilesAppSave: NSObject {

@objc
func startDownload(_ urlString: String, resolve: @escaping RCTPromiseResolveBlock, reject: @escaping RCTPromiseRejectBlock) {
    guard let url = URL(string: urlString) else {
        reject("Error", "Invalid URL", nil)
        return
    }

    let task = URLSession.shared.dataTask(with: url) { (data, response, error) in
        if let error = error {
            reject("Error", "Error downloading file: \(error)", nil)
            return
        }

        guard let httpResponse = response as? HTTPURLResponse, (200...299).contains(httpResponse.statusCode) else {
            reject("Error", "Invalid status code", nil)
            return
        }

        guard let data = data else {
            reject("Error", "No data downloaded", nil)
            return
        }

        let fileName = url.lastPathComponent
        let resDocPath = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).last!
        let filePath = resDocPath.appendingPathComponent(fileName)

        do {
            try data.write(to: filePath)
            resolve("File saved")
        } catch {
            reject("Error", "Error creating file: \(error)", nil)
        }
    }

    task.resume()
}


  @objc(multiply:withB:withResolver:withRejecter:)
  func multiply(a: Float, b: Float, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) -> Void {
    resolve(a*b)
  }
}
