// Karma configuration
// http://karma-runner.github.io/0.10/config/configuration-file.html

module.exports = function (config) {
    config.set({
        // base path, that will be used to resolve files and exclude
        basePath: '../../',

        // testing framework to use (jasmine/mocha/qunit/...)
        frameworks: ['jasmine'],

        // list of files / patterns to load in the browser
        files: [
            // bower:js
            'main/webapp/bower_components/jquery/dist/jquery.js',
            'main/webapp/bower_components/angular/angular.js',
            'main/webapp/bower_components/angular-sanitize/angular-sanitize.js',
            'main/webapp/bower_components/angular-animate/angular-animate.js',
            'main/webapp/bower_components/bootstrap/dist/js/bootstrap.js',
            'main/webapp/bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
            'main/webapp/bower_components/angular-ui-router/release/angular-ui-router.js',
            'main/webapp/bower_components/angular-ui-layout/src/ui-layout.js',
            'main/webapp/bower_components/angular-ui-select/dist/select.js',
            'main/webapp/bower_components/angular-social/angular-social.js',
            'main/webapp/bower_components/angular-ui-grid/ui-grid.js',
            'main/webapp/bower_components/FileSaver/FileSaver.js',
            'main/webapp/bower_components/underscore/underscore.js',
            'main/webapp/bower_components/ng-file-upload/ng-file-upload.js',
            'main/webapp/bower_components/ng-file-upload-shim/ng-file-upload-shim.js',
            'main/webapp/bower_components/angular-scroll/angular-scroll.js',
            'main/webapp/bower_components/wow/dist/wow.js',
            'main/webapp/bower_components/angular-mocks/angular-mocks.js',
            // endbower
            'main/webapp/scripts/app/app.js',
            'main/webapp/scripts/app/**/*.js',
            'main/webapp/scripts/components/**/*.+(js|html)',
            'test/javascript/**/!(karma.conf).js'
        ],


        // list of files / patterns to exclude
        exclude: [],

        preprocessors: {
            'main/webapp/scripts/components/**/*.html' : ['ng-html2js'],
            './**/*.js': ['coverage']
        },

        ngHtml2JsPreprocessor: {
            // strip this from the file path
            stripPrefix: 'main/webapp/'
        },

        reporters: ['dots', 'jenkins', 'coverage', 'progress'],

        jenkinsReporter: {
            
            outputFile: '../target/test-results/karma/TESTS-results.xml'
        },

        coverageReporter: {
            
            dir: '../target/test-results/coverage',
            reporters: [
                {type: 'lcov', subdir: 'report-lcov'}
            ]
        },

        // web server port
        port: 9876,

        // level of logging
        // possible values: LOG_DISABLE || LOG_ERROR || LOG_WARN || LOG_INFO || LOG_DEBUG
        logLevel: config.LOG_INFO,

        // enable / disable watching file and executing tests whenever any file changes
        autoWatch: false,

        // Start these browsers, currently available:
        // - Chrome
        // - ChromeCanary
        // - Firefox
        // - Opera
        // - Safari (only Mac)
        // - PhantomJS
        // - IE (only Windows)
        browsers: ['PhantomJS'],

        // Continuous Integration mode
        // if true, it capture browsers, run tests and exit
        singleRun: false,
        // to avoid DISCONNECTED messages when connecting to slow virtual machines
        browserDisconnectTimeout : 10000, // default 2000
        browserDisconnectTolerance : 1, // default 0
        browserNoActivityTimeout : 4*60*1000 //default 10000
    });
};
