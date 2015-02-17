droidlinguist
===========================================================================================================================================

A tool that helps you easily translate your killer android apps in a nice & clear interface, using popular translator engines like [Microsoft Translator](http://aka.ms/MicrosoftTranslatorAttribution), [Yandex Translate](http://translate.yandex.com/) or [Google Translate](http://translate.google.com)!

# Features & Demo

[DEMO](http://droidlinguist-tinesoft.rhcloud.com/)

![DroidLinguit Screenshot](/droidlinguist-web/src/main/webapp/assets/images/screenshot-translate.png)

# Development

### Requirements

You should already have the following dependencies installed: **Java 8**, **Maven**, **Node.js(npm)**, **Bower** and **Grunt**. if not:

* Java 8
* Install [Maven](https://maven.apache.org/download.cgi)
* Install [Node.js](http://nodejs.org/). This will also install npm, which is the node package manager we are using in the next commands.
* Install [Bower](http://bower.io): ```npm install -g bower```
* Install [Grunt](http://gruntjs.com): ```npm install -g grunt-cli```

### Overall Directory Structure

The project structure looks like this:

```
droidlinguist/
  |- droidlinguist-web/
  |- droidlinguist-server/
```

### Usage

Once installed, simply run the following command from the project *root directory* to install all maven dependencies locally:

```
$ mvn clean install 
```

Then, move to **droidlinguist-web** folder and run the following to start the local server (@ [http:localhost:8080](http:localhost:8080)):

```
$ mvn [<TRANSLATOR_OPTIONS>] -P<PROFILE> 
```
where ```<PROFILE>``` can be either:
* **dev** : developpment profile (unminified js/html to ease debbugging, Tomcat application server)
* **fast** : another developpment profile (unminified js/html/css to ease debbugging, Undertow application server to start faster)
* **prod** : production profile (minified js/html/css, static resources caching, ...)

and ```<TRANSLATOR_OPTIONS>``` are (not exclusive):
* **-Dtranslator.microsoft.clientId=```<YOUR_CLIENT_ID>``` -Dtranslator.microsoft.clientSecret=```<YOUR_CLIENT_SECRET>```** : to use Microsoft Translator engine
* **-Dtranslator.yandex.apiKey=```<YOUR_API_KEY>```** : to use Yandex Translate engine
* **-Dtranslator.google.apiKey=```<YOUR_API_KEY>```** : to use Google Translate engine

**Note:** If you don't want to use a translator engine at all, simply ignore these properties.


Observe the server starting logs to make sure your setup is correct.

If no warnings is shown, then run the following (in another console):

```
$ grunt serve 
```

That will start a proxy of local server at [http:localhost:3000](http:localhost:3000) and open a new browser tab.
You can browse the application from that URL.

Also check out [http:localhost:3001](http:localhost:3001). It hosts a tool named **Browser Sync**, that manages the app at [http:localhost:3000](http:localhost:3000) and provides cool features like live reload, css outlining, css overlay...

# License

Copyright (c) 2016 Tine Kondo. Licensed under the MIT License (MIT)

