# How to Enable Wear OS Release Track

If you cannot find the "Wear OS" track in your Google Play Console, you need to enable the Wear OS form factor first.

## Step-by-Step Guide

1.  **Log in** to your [Google Play Console](https://play.google.com/console).
2.  **Select your app** (WatchMyMoney).
3.  In the left-hand menu, scroll down to the **Release** section.
4.  Expand **Setup** and click on **Advanced settings**.
5.  On the Advanced settings page, look for the **Form factors** tab.
6.  Click the **+ Add form factor** button.
7.  Select **Wear OS**.
8.  Follow the prompts to upload screenshots and set up the store listing for Wear OS if requested (you can often skip this initially or use your existing assets).
9.  **Save** your changes.

## After Enabling

Once you have added the Wear OS form factor:

1.  Look at the left-hand menu again under **Release**.
2.  You should now see a dedicated **Wear OS** section or tracks labeled with a watch icon.
3.  Go to **Production** (or Open Testing).
4.  In the top right of the release dashboard, there might be a dropdown to switch between "Phone/Tablet" and "Wear OS". **Make sure "Wear OS" is selected.**
5.  Click **Create new release** within this Wear OS context.
6.  Upload your `.aab` file here.

> **Note:** Google requires Wear OS apps to be "Standalone" (which we configured in the Manifest) and to have a dedicated release track.
