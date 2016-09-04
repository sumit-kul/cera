/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	config.removeButtons = 'Save,Print,Form,Checkbox,Radio,TextField,Textarea,Select,HiddenField,Button,About,NewPage,ImageButton';
	config.toolbarLocation = 'top';
	config.allowedContent = true;
	config.extraAllowedContent = 'pre[*]{*}(*)';
	config.extraPlugins = "imageresize";
	config.removePlugins = "scayt";
};

CKEDITOR.editorConfig.imageResize.maxWidth = 700; 
CKEDITOR.editorConfig.imageResize.maxHeight = 700; 



