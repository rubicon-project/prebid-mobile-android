package org.prebid.mobile.demoapp;

import android.support.test.espresso.web.matcher.DomMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.xamarin.testcloud.espresso.Factory;
import com.xamarin.testcloud.espresso.ReportHelper;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webContent;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.instanceOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RubiconHostPrebidTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Rule
    public ReportHelper reportHelper = Factory.getReportHelper();

    @Test
    public void testDFPBannerLoadsPrebidCreative() {
        onView(withText("DFP")).perform(click());
        onView(withText("Show Banner Example")).perform(click());

        sleep(2);

        // Assert PublisherAdView is created and added to the view hierarchy properly
        reportHelper.label("Assert that prebid creative served");
        onView(nthChildOf(withId(R.id.adFrame), 0)).check(matches(withClassName(endsWith("PublisherAdView"))));

        onView(withText("Refresh Banner")).perform(click());

        sleep(5);

        Matcher<View> dfpWebView = nthChildOf(nthChildOf(nthChildOf(nthChildOf(withId(R.id.adFrame), 0), 0), 0), 0);
        onView(dfpWebView)
                .check(matches(instanceOf(WebView.class)));
        onWebView(dfpWebView)
                .check(webContent(DomMatchers.containingTextInBody("pbm.showAdFromCacheId")));
    }

    @Test
    public void testMoPubBannerLoadsPrebidCreative() {
        onView(withText("MoPub")).perform(click());
        onView(withText("Show Banner Example")).perform(click());

        sleep(2);

        // Assert PublisherAdView is created and added to the view hierarchy properly
        reportHelper.label("Assert that prebid creative served");
        onView(nthChildOf(withId(R.id.adFrame), 0)).check(matches(withClassName(endsWith("MoPubView"))));

        onView(withText("Refresh Banner")).perform(click());

        sleep(5);

        Matcher<View> dfpWebView = nthChildOf(nthChildOf(nthChildOf(withId(R.id.adFrame), 0), 0), 0);
        onView(dfpWebView)
                .check(matches(instanceOf(WebView.class)));
        onWebView(dfpWebView)
                .check(webContent(DomMatchers.containingTextInBody("pbm.showAdFromCacheId")));
    }

    @Test
    public void testDFPInterstitialLoadsPrebidCreative() {
        onView(withText("DFP")).perform(click());
        onView(withText("Show Interstitial Example")).perform(click());

        sleep(3);

        // Assert PublisherAdView is created and added to the view hierarchy properly
        reportHelper.label("Assert that prebid creative served");

        onView(withText("Load Interstitial")).perform(click());

        sleep(5);

        Matcher<View> dfpWebView = nthChildOf(nthChildOf(nthChildOf(withId(android.R.id.content), 0), 0), 0);
        onView(dfpWebView)
                .check(matches(instanceOf(WebView.class)));
        onWebView(dfpWebView)
                .check(webContent(DomMatchers.containingTextInBody("pbm.showAdFromCacheId")));
    }

    @Test
    public void testMoPubInterstitialLoadsPrebidCreative() {
        onView(withText("MoPub")).perform(click());
        onView(withText("Show Interstitial Example")).perform(click());

        sleep(2);

        // Assert PublisherAdView is created and added to the view hierarchy properly
        reportHelper.label("Assert that prebid creative served");
        //onView(nthChildOf(withId(R.id.adFrame), 0)).check(matches(withClassName(endsWith("MoPubView"))));

        onView(withText("Load Interstitial")).perform(click());

        sleep(5);

        Matcher<View> dfpWebView = nthChildOf(nthChildOf(nthChildOf(withId(android.R.id.content), 0), 0), 0);
        onView(dfpWebView)
                .check(matches(instanceOf(WebView.class)));
        onWebView(dfpWebView)
                .check(webContent(DomMatchers.containingTextInBody("pbm.showAdFromCacheId")));
    }

    public static Matcher<View> nthChildOf(final Matcher<View> parentMatcher, final int childPosition) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with " + childPosition + " child view of type parentMatcher");
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view.getParent() instanceof ViewGroup)) {
                    return parentMatcher.matches(view.getParent());
                }

                ViewGroup group = (ViewGroup) view.getParent();
                System.out.println("group " + group.toString());
                return parentMatcher.matches(view.getParent()) && group.getChildAt(childPosition).equals(view);
            }
        };
    }

    private void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }

}

