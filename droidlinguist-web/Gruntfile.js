'use strict';

// usemin custom step
var useminAutoprefixer = {
    name: 'autoprefixer',
    createConfig: function(context, block) {
        if(block.src.length === 0) {
            return {};
        } else {
            return require('grunt-usemin/lib/config/cssmin').createConfig(context, block) // Reuse cssmins createConfig
        }
    }
};

module.exports = function (grunt) {
    require('load-grunt-tasks')(grunt);
    require('time-grunt')(grunt);

    grunt.initConfig({

        copyright: 'Copyright &copy; Tinesoft '+ grunt.template.today('yyyy'),
        /**
        * We read in our `package.json` file so we can access the package name and
        * version. It's already there, so we don't repeat ourselves here.
        */
        pkg: grunt.file.readJSON("package.json"),

        yeoman: {
            // configurable paths
            app: require('./bower.json').appPath || 'app',
            dist: 'src/main/webapp/dist'
        },
        app: {
            // Application variables
            scripts: [
                   // JS files to be included by includeSource task into index.html
                   'scripts/app/app.js',
                   'scripts/app/app.constants.js',
                   'scripts/components/**/*.js',
                   'scripts/app/**/*.js',
                   '!scripts/components/**/*.spec.js',
                   '!scripts/app/**/*.spec.js'
                ]
        },
        includeSource: {
            // Task to include files into index.html
            options: {
                basePath: 'src/main/webapp',
                baseUrl: '',
                ordering: 'top-down'
            },
            app: {
                files: {
                    'src/main/webapp/index.html': 'src/main/webapp/index.html'
                    // you can add karma config as well here if want inject to karma as well
                }
            }
        },
        watch: {
            bower: {
                files: ['bower.json'],
                tasks: ['wiredep']
            },
            ngconstant: {
                files: ['Gruntfile.js', 'pom.xml'],
                tasks: ['ngconstant:dev']
            },
            less: {
                files: ['src/main/less/**/*.less'],
                tasks: ['less:server']
            },
            includeSource: {
                // Watch for added and deleted scripts to update index.html
                files: 'src/main/webapp/scripts/**/*.js',
                tasks: ['includeSource'],
                options: {
                    event: ['added', 'deleted']
                }
            }
            //,
            //jshint: {
            //    files: [
            //    'Gruntfile.js',
            //    'src/main/webapp/scripts/app.js',
            //    'src/main/webapp/scripts/app/**/*.js',
            //    'src/main/webapp/scripts/components/**/*.js'
            //    ],
            //    tasks: [ 'jshint:all' ]
            //}
        },
        autoprefixer: {
        // not used since Uglify task does autoprefixer,
        //    options: ['last 1 version'],
        //    dist: {
        //        files: [{
        //            expand: true,
        //            cwd: '.tmp/styles/',
        //            src: '**/*.css',
        //            dest: '.tmp/styles/'
        //        }]
        //    }
        },
        wiredep: {
            app: {
                src: ['src/main/webapp/index.html', 'src/main/less/main.less'],
                exclude: [
                    /angular-i18n/, // localizations are loaded dynamically
                    /swagger-ui/,
                    'bower_components/bootstrap/dist/css/' // Exclude Bootstrap CSS as we use bootstrap less
                ],
                ignorePath: /\.\.\/webapp\/bower_components\//, // remove ../webapp/bower_components/ from paths of injected less files,
                overrides:{
                    "flag-icon-css":{
                        "main": ['less/flag-icon.less']
                    },
                    "animate.less":{
                        "main" : ['source/animated.less', 'source/pulse.less', 'source/shake.less',
                                  'source/fadeInUp.less', 'source/fadeOutLeft.less',
                                  'source/bounceIn.less', 'source/bounceOut.less',
                                  'source/bounceInLeft.less', 'source/bounceInRight.less']
                    }
                }
            },
            test: {
                src: 'src/test/javascript/karma.conf.js',
                exclude: [/angular-i18n/, /swagger-ui/, /angular-scenario/],
                ignorePath: /\.\.\/\.\.\//, // remove ../../ from paths of injected javascripts
                devDependencies: true,
                fileTypes: {
                    js: {
                        block: /(([\s\t]*)\/\/\s*bower:*(\S*))(\n|\r|.)*?(\/\/\s*endbower)/gi,
                        detect: {
                            js: /'(.*\.js)'/gi
                        },
                        replace: {
                            js: '\'{{filePath}}\','
                        }
                    }
                }
            }
        },
        browserSync: {
            dev: {
                bsFiles: {
                    src : [
                        'src/main/webapp/**/*.html',
                        'src/main/webapp/**/*.json',
                        'src/main/webapp/assets/styles/**/*.css',
                        'src/main/webapp/scripts/**/*.{js,html}',
                        'src/main/webapp/assets/images/**/*.{png,jpg,jpeg,gif,webp,svg}',
                        'tmp/**/*.{css,js}'
                    ]
                }
            },
            options: {
                watchTask: true,
                proxy: "localhost:8080"
            }
        },
        clean: {
            dist: {
                files: [{
                    dot: true,
                    src: [
                        '.tmp',
                        '<%= yeoman.dist %>/*',
                        '!<%= yeoman.dist %>/.git*'
                    ]
                }]
            },
            server: '.tmp'
        },
        jshint: {
            options: {
                jshintrc: '.jshintrc'
            },
            all: [
                'Gruntfile.js',
                'src/main/webapp/scripts/app.js',
                'src/main/webapp/scripts/app/**/*.js',
                'src/main/webapp/scripts/components/**/*.js'
            ]
        },
        less: {
            options: {
                paths: ['src/main/webapp/bower_components']
             },
            server: {
                options: {
                    //sourceMap: true,
                    //sourceMappingURL: '',//not working see #236
                    //dumpLineNumbers: 'comments'
                },
                files: {
                    'src/main/webapp/assets/styles/main.css' : 'src/main/less/main.less',
                }
            }
        },
        concat: {
        // not used since Uglify task does concat,
        // but still available if needed
        //    dist: {}
        },
        rev: {
            dist: {
                files: {
                    src: [
                        '<%= yeoman.dist %>/scripts/**/*.js',
                        '<%= yeoman.dist %>/assets/styles/**/*.css',
                        '<%= yeoman.dist %>/assets/images/**/*.{png,jpg,jpeg,gif,webp,svg}',
                        '<%= yeoman.dist %>/assets/fonts/*'
                    ]
                }
            }
        },
        useminPrepare: {
            html: 'src/main/webapp/**/*.html',
            options: {
                dest: '<%= yeoman.dist %>',
                flow: {
                    html: {
                        steps: {
                            js: ['concat', 'uglifyjs'],
                            css: ['cssmin', useminAutoprefixer] // Let cssmin concat files so it corrects relative paths to fonts and images
                        },
                            post: {}
                        }
                    }
            }
        },
        usemin: {
            html: ['<%= yeoman.dist %>/**/*.html'],
            css: ['<%= yeoman.dist %>/assets/styles/**/*.css'],
            js: ['<%= yeoman.dist %>/scripts/**/*.js'],
            options: {
                assetsDirs: ['<%= yeoman.dist %>', '<%= yeoman.dist %>/assets/styles', '<%= yeoman.dist %>/assets/images', '<%= yeoman.dist %>/assets/fonts'],
                patterns: {
                    js: [
                        [/(assets\/images\/.*?\.(?:gif|jpeg|jpg|png|webp|svg))/gm, 'Update the JS to reference our revved images']
                    ]
                },
                dirs: ['<%= yeoman.dist %>']
            }
        },
        imagemin: {
            dist: {
                files: [{
                    expand: true,
                    cwd: 'src/main/webapp/assets/images',
                    src: '**/*.{png,jpg,jpeg}', // we don't optimize PNG files as it doesn't work on Linux. If you are not on Linux, feel free to use '**/*.{png,jpg,jpeg}'
                    dest: '<%= yeoman.dist %>/assets/images'
                }]
            }
        },
        svgmin: {
            dist: {
                files: [{
                    expand: true,
                    cwd: 'src/main/webapp/assets/images',
                    src: '**/*.svg',
                    dest: '<%= yeoman.dist %>/assets/images'
                }]
            }
        },
        cssmin: {
            // By default, your `index.html` <!-- Usemin Block --> will take care of
            // minification. This option is pre-configured if you do not wish to use
            // Usemin blocks.
            // dist: {
            //     files: {
            //         '<%= yeoman.dist %>/styles/main.css': [
            //             '.tmp/styles/**/*.css',
            //             'styles/**/*.css'
            //         ]
            //     }
            // }
            options: {
                root: 'src/main/webapp' // Replace relative paths for static resources with absolute path
            }
        },
        ngtemplates:    {
            dist: {
                cwd: 'src/main/webapp',
                src: ['scripts/app/**/*.html', 'scripts/components/**/*.html',],
                dest: '.tmp/templates/templates.js',
                options: {
                    module: 'droidlinguistApp',
                    usemin: 'scripts/app.js',
                    htmlmin: '<%= htmlmin.dist.options %>'
                }
            }
        },
        htmlmin: {
            dist: {
                options: {
                    removeCommentsFromCDATA: true,
                    // https://github.com/yeoman/grunt-usemin/issues/44
                    collapseWhitespace: true,
                    collapseBooleanAttributes: true,
                    conservativeCollapse: true,
                    removeAttributeQuotes: true,
                    removeRedundantAttributes: true,
                    useShortDoctype: true,
                    removeEmptyAttributes: true,
                    keepClosingSlash: true
                },
                files: [{
                    expand: true,
                    cwd: '<%= yeoman.dist %>',
                    src: ['*.html'],
                    dest: '<%= yeoman.dist %>'
                }]
            }
        },
        // Put files not handled in other tasks here
        copy: {
            dist: {
                files: [{
                    expand: true,
                    dot: true,
                    cwd: '<%= yeoman.app %>',
                    dest: '<%= yeoman.dist %>',
                    src: [
                        '*.html',
                        'scripts/**/*.html',
                        'assets/images/**/*.{png,gif,webp,jpg,jpeg,svg}',
                        'assets/fonts/*'
                    ]
                }, {
                    expand: true,
                    cwd: '.tmp/assets/images',
                    dest: '<%= yeoman.dist %>/assets/images',
                    src: [
                        'generated/*'
                    ]
                }]
            }, 
            generateOpenshiftDirectory: {
                files: [{src: 'target/droidlinguist-web-*.war', dest: '../deploy/openshift/target/droidlinguist-web.war'}]
            }
        },
        karma: {
            unit: {
                configFile: 'src/test/javascript/karma.conf.js',
                singleRun: true
            }
        },
        cdnify: {
            dist: {
                html: ['<%= yeoman.dist %>/*.html']
            }
        },
        ngAnnotate: {
            dist: {
                files: [{
                    expand: true,
                    cwd: '.tmp/concat/scripts',
                    src: '*.js',
                    dest: '.tmp/concat/scripts'
                }]
            }
        },
        buildcontrol: {
            options: {
                commit: true,
                push: false,
                connectCommits: false,
                message: 'Built %sourceName% from commit %sourceCommit% on branch %sourceBranch%'
            },
            openshift: {
                options: {
                    dir: '../deploy/openshift',
                    remote: 'origin',
                    branch: 'master'
                }
            }
        },
        ngconstant: {
            options: {
                name: 'droidlinguist.constants',
                deps: [],
                wrap: '"use strict";\n// DO NOT EDIT THIS FILE, EDIT THE GRUNT TASK NGCONSTANT SETTINGS INSTEAD WHICH GENERATES THIS FILE\n{%= __ngModule %}'
            },
            dev: {
                options: {
                    dest: 'src/main/webapp/scripts/app/app.constants.js'
                },
                constants: {
                    BUILD_OPTIONS:{
                        env: 'dev',
                        version: '<%= pkg.version %>',
                        copyright: '<%= copyright %>',
                        description: '<%= pkg.description %>'
                    }
                }
            },
            prod: {
                options: {
                    dest: '.tmp/scripts/app/app.constants.js'
                },
                constants: {
                    BUILD_OPTIONS:{
                        env: 'prod',
                        version: '<%= pkg.version %>',
                        copyright: '<%= copyright %>',
                        description: '<%= pkg.description %>'
                    }
                }
            }
        }
    });

    grunt.registerTask('serve', [
        'clean:server',
        'wiredep',
        'includeSource',
        'ngconstant:dev',
        'less:server',    
        'browserSync',
        'watch'
    ]);

    grunt.registerTask('server', function (target) {
        grunt.log.warn('The `server` task has been deprecated. Use `grunt serve` to start a server.');
        grunt.task.run([target ? ('serve:' + target) : 'serve']);
    });

    grunt.registerTask('test', [
        'clean:server',
        'wiredep:test',
        'ngconstant:dev',
        'less:server',
        'karma'
    ]);

    grunt.registerTask('build', [
        'clean:dist',
        'wiredep:app',
        'includeSource',
        'ngconstant:prod',
        'useminPrepare',
        'ngtemplates',
        'less:server',
        'concat',
        'copy:dist',
        'ngAnnotate',
        'cssmin',
        'autoprefixer',
        'uglify',
        'rev',
        'usemin',
        'htmlmin'
    ]);

    grunt.registerTask('buildOpenshift', [
        'test',
        'build',
        'copy:generateOpenshiftDirectory',
    ]);

    grunt.registerTask('deployOpenshift', [
        'test',
        'build',
        'copy:generateOpenshiftDirectory',
        'buildcontrol:openshift'
    ]);

    grunt.registerTask('copyDeployOpenshift', [
        'copy:generateOpenshiftDirectory',
        'buildcontrol:openshift'
    ]);

    grunt.registerTask('default', [
        'test',
        'build'
    ]);
};
