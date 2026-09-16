import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        AppModule_iosKt.initKoinIos()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
