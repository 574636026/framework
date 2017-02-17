/**
 *  Injedu UI Starter Kit
 *
 *  Copyright 2014 Google Inc. All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 *
 */

'use strict';

var path = require('path');
var fs = require('fs');
var format = require('util').format;
var _ = require('lodash');
var del = require('del');

var runSequence = require('run-sequence');
var gulp = require('gulp');
var $ = require('gulp-load-plugins')();

var pkg = require('./package.json');

var config = {
    path: {
        css: {
            custom : './src/css/custom/*.css',
            plugin : './src/css/plugin/*.css'
        },
	    js : {
	    	ui : './src/js/custom/ui/*.js',
	    	utils :
	    		[
	    			'./src/js/custom/utils/utils.injedu.const.js','./src/js/custom/utils/utils.injedu.tools.js','./src/js/custom/utils/utils.injedu.dialog.js',
	    			'./src/js/custom/utils/utils.injedu.http.js','./src/js/custom/utils/utils.injedu.window.js','./src/js/custom/utils/utils.injedu.validate.js',
	    			'./src/js/custom/utils/utils.injedu.socket.js'
	    		],
	    	plugin : ['./src/js/plugin/hight/moment.min.js','./src/js/plugin/*.js'],
	    	datatables:['./src/js/plugin/datatables/amazeui.datatables.js'],
	    	fileupload : ['./src/js/plugin/fileupload/load-image.all.js','./src/js/plugin/fileupload/jquery.ui.widget.js','./src/js/plugin/fileupload/jquery.fileupload.js',
	    				 './src/js/plugin/fileupload/jquery.fileupload-process.js','./src/js/plugin/fileupload/jquery.fileupload-image.js','./src/js/plugin/fileupload/jquery.fileupload-validate.js',
                          './src/js/plugin/fileupload/amazeui.fileupload-ui.js']
	    }
    },
    dist: {
        js: './dist/js/custom',
        css: './dist/css'
    },
    uglify: {
        compress: {
            warnings: false
        },
        output: {
            ascii_only: true
        }
    }
};

var dateFormat = 'isoDateTime';
var NODE_ENV = process.env.NODE_ENV;
var banner = [
    '/*! <%= pkg.title %> v<%= pkg.version %><%=ver%>',
    'by <%= pkg.author%>',
    'Date ' + $.util.date(Date.now(), 'UTC:yyyy'),
    'Licensed under <%= pkg.license %>',
    $.util.date(Date.now(), dateFormat) + ' */ \n'
].join(' | ');

gulp.task('build:clean', function() {
    return del([
        'amazeui.plugin.min.css',
        pkg.name +'.min.css',
        config.dist.js
    ]);
});

// pack utils
gulp.task('build:js:utils', function() {
    return gulp.src(config.path.js.utils)
        .pipe($.concat(pkg.name +'.utils.js'))
        .pipe(gulp.dest(config.dist.js))
        .pipe($.uglify(config.uglify))
        .pipe($.rename({suffix: '.min'}))
        .pipe(gulp.dest(config.dist.js))
        .pipe($.size({showFiles: true, title: 'minified'}))
        .pipe($.size({showFiles: true, gzip: true, title: 'gzipped'}));
});

// pack ui
gulp.task('build:js:ui', function() {
    return gulp.src(config.path.js.ui)
        .pipe($.concat(pkg.name +'.ui.js'))
        .pipe(gulp.dest(config.dist.js))
        .pipe($.uglify(config.uglify))
        .pipe($.rename({suffix: '.min'}))
        .pipe(gulp.dest(config.dist.js))
        .pipe($.size({showFiles: true, title: 'minified'}))
        .pipe($.size({showFiles: true, gzip: true, title: 'gzipped'}));
});

// pack js
gulp.task('build:js:pack', function() {
    return gulp.src([config.dist.js + '/' + pkg.name +'.utils.js',config.dist.js + '/' + pkg.name +'.ui.js'])
        .pipe($.concat(pkg.name +'.js'))
        .pipe($.header(banner, {pkg: pkg, ver: ''}))
        .pipe(gulp.dest(config.dist.js))
        .pipe($.uglify(config.uglify))
        .pipe($.header(banner, {pkg: pkg, ver: ''}))
        .pipe($.rename({suffix: '.min'}))
        .pipe(gulp.dest(config.dist.js))
        .pipe($.size({showFiles: true, title: 'minified'}))
        .pipe($.size({showFiles: true, gzip: true, title: 'gzipped'}));
});

//pack the plugin datatables
gulp.task('build:js:plugin:datatables', function() {
    return gulp.src(config.path.js.datatables)
        .pipe($.concat('amazeui.datatables.js'))
        .pipe(gulp.dest('./src/js/plugin'))
});

// pack the plugin fileupload
gulp.task('build:js:plugin:fileupload', function() {
    return gulp.src(config.path.js.fileupload)
        .pipe($.concat('amazeui.fileupload.js'))
        .pipe(gulp.dest('./src/js/plugin'))
});

// pack the plugin
gulp.task('build:js:plugin:pack', function() {
    return gulp.src(config.path.js.plugin)
        .pipe($.concat('amazeui.plugin.js'))
        .pipe($.uglify(config.uglify))
        .pipe($.rename({suffix: '.min'}))
        .pipe(gulp.dest(config.dist.js))
        .pipe($.size({showFiles: true, title: 'minified'}))
        .pipe($.size({showFiles: true, gzip: true, title: 'gzipped'}));
});

gulp.task('build:js:plugin', function(cb) {
    runSequence(
        ['build:js:plugin:datatables','build:js:plugin:fileupload'],
        'build:js:plugin:pack',
        cb);
});

// bulid custom js
gulp.task('build:js:custom', function(cb) {
    runSequence(
    	['build:js:utils','build:js:ui'],
    	'build:js:pack',
        cb);
});

// bulid all js
gulp.task('build:js', function(cb) {
    runSequence(
    	['build:js:custom','build:js:plugin'],
        cb);
});

// build css costom
gulp.task('build:css:custom', function() {
    return gulp.src(config.path.css.custom)
        .pipe($.concat(pkg.name +'.css'))
        .pipe($.minifyCss({noAdvanced: true}))
        .pipe($.rename({suffix: '.min'}))
        .pipe(gulp.dest(config.dist.css))
        .pipe($.size({showFiles: true, title: 'minified'}))
        .pipe($.size({showFiles: true, gzip: true, title: 'gzipped'}));
});

gulp.task('build:css:plugin', function() {
    return gulp.src(config.path.css.plugin)
        .pipe($.concat('amazeui.plugin.css'))
        .pipe($.minifyCss({noAdvanced: true}))
        .pipe($.rename({suffix: '.min'}))
        .pipe(gulp.dest(config.dist.css))
        .pipe($.size({showFiles: true, title: 'minified'}))
        .pipe($.size({showFiles: true, gzip: true, title: 'gzipped'}));
});

gulp.task('build:css', function(cb) {
    runSequence(
        ['build:css:custom','build:css:plugin'],
        cb);
});

// bulid all task
gulp.task('build', function(cb) {
    runSequence(
        'build:clean',
        ['build:js'],
        'build:css',
        cb);
});

// Rerun the task when a file changes
gulp.task('watch', function() {
	gulp.watch([config.path.js.utils], ['build:js:custom']);
    gulp.watch([config.path.js.ui], ['build:js:custom']);
    
    gulp.watch([config.path.js.plugin], ['build:js:plugin:pack']);
    gulp.watch([config.path.js.fileupload], ['build:js:plugin']);
    gulp.watch([config.path.css.custom], ['build:css:custom']);
    gulp.watch([config.path.css.plugin], ['build:css:plugin']);
});

gulp.task('default', ['build','watch']);

gulp.task('plugin', ['build:js:plugin']);
