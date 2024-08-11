# Download

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

Project gradle:
```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

Gradle:
```gradle
dependencies {
    implementation 'com.github.pvnam198:Alibs:tag'
}
```

How do I use Lib?
-------------------
```kotlin

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var adsManager: AdsManager

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            adsManager.init()
        }
    }

}

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {

        private const val APP_OPEN_AD_UNIT_ID = "ca-app-pub-3940256099942544/9257395921"

        private const val REWARD_AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917"

    }

    @Inject
    lateinit var admobBannerManager: AdmobBannerManager

    @Inject
    lateinit var nativeAdRepository: NativeAdRepository

    @Inject
    lateinit var interstitialAdManager: InterstitialAdManager

    @Inject
    lateinit var adOpenAdManager: AdOpenAdManager

    @Inject
    lateinit var rewardAdManager: RewardAdManager

    private lateinit var bannerView: FrameLayout
    private lateinit var templateView: TemplateView
    private lateinit var btnLoadNativeAd: View
    private lateinit var btnLoadInterstitial: View
    private lateinit var btnShowInterstitial: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        templateView = findViewById(R.id.template_view)
        btnLoadNativeAd = findViewById(R.id.btn_load_native)
        bannerView = findViewById(R.id.banner_view)
        btnLoadInterstitial = findViewById(R.id.btn_load_interstitial)
        btnShowInterstitial = findViewById(R.id.btn_show_interstitial)

        adOpenAdManager.load(APP_OPEN_AD_UNIT_ID)

        // Get nativeAd
        val nativeAd: NativeAd? = nativeAdRepository.getNativeAd()

        // Get specialNativeAd with id "main_screen"
        // If there is a specialNativeAd marked as main_screen return it else...
        // ...if not exists, load new native, otherwise return available native and mark as main_screen
        var specialNativeAd: NativeAd? = nativeAdRepository.getNativeAd("main_screen")

        // Release native with id "main_screen"
        // nativeAdRepository.releaseNative("main_screen")

        val nativeAdChangedListener = object : NativeAdChangedListener {
            override fun onNativeChanged() {
                specialNativeAd = nativeAdRepository.getNativeAd("main_screen")
                Log.d("log_debugs", "MainActivity_onNativeChanged: $specialNativeAd")
                specialNativeAd?.let {
                    templateView.setNativeAd(it)
                }
            }
        }

        // Listen native ad change
        nativeAdRepository.addListener(nativeAdChangedListener)

        // Remove listener
        // nativeAdRepository.removeListener(nativeAdChangedListener)

        // Set NativeAdSetting
        nativeAdRepository.setNativeAdSetting("json")

        // Load native
        nativeAdRepository.load("ca-app-pub-3940256099942544/2247696110")

        // Load banner
        admobBannerManager.load(
            "ca-app-pub-3940256099942544/2014213617",
            bannerSize = null,
            type = null,
            callback = { adView ->
                if (adView != null) {
                    bannerView.visibility = View.VISIBLE
                    bannerView.removeAllViews()
                    bannerView.addView(adView)
                } else {
                    bannerView.visibility = View.GONE
                }
            })

        // bannerView with show if loaded else hide
        admobBannerManager.displayAdIfLoaded(
            adUnitId = "ca-app-pub-3940256099942544/2014213617",
            parent = bannerView,
            bannerSize = BannerSize.FitParent(bannerView)
        )

        // Load interstitial ad
        interstitialAdManager.load("ca-app-pub-3940256099942544/1033173712")

        // Show interstitial ad
        btnShowInterstitial.setOnClickListener {

            // If preloadAdUnitId != null, load new interstitial ad
            interstitialAdManager.show(this, onDismiss = {

            }, preloadAdUnitId = "ca-app-pub-3940256099942544/1033173712")
        }

        rewardAdManager.load(REWARD_AD_UNIT_ID, onSucceed = {
            Log.d(TAG, "onSucceedRewardAd")
            rewardAdManager.show(this, onDismiss = {
                Log.d(TAG, "onDismissRewardAd reward: $it")
            }, preloadAdUnitId = REWARD_AD_UNIT_ID)
        }, onFailed = {
            Log.d(TAG, "onFailedRewardAd: $it")
        })
    }

    override fun onResume() {
        super.onResume()
        adOpenAdManager.show(this, onDismiss = {
            Log.d(TAG, "onDismissOpenAd")
        }, preloadAdUnitId = null)
    }

}
```


### Requirements
#### Dependency injection with Hilt
- https://developer.android.com/training/dependency-injection/hilt-android
