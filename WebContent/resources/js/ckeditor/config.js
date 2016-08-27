/**
 * @license Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	config.language = 'zh-cn';
	config.uiColor = '#61B0FF';
	// 移除最低底部的提示栏
	config.removePlugins = 'elementspath';
	// 不能改变大小
	config.resize_enabled = false;
	
//	config.skin = 'office2013';
	config.skin = 'moono_blue';
//	config.skin = 'moonocolor';

	config.toolbar_Full =
	[
		['Preview','-','Templates'],
		['Cut','Copy','Paste','PasteText','PasteFromWord','-','Print','Scayt'],
		['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],
		['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton'],
		'/',
		['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
		['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
		['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
		['Link','Unlink','Anchor'],
		['Image','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
		'/',
		['Styles','Format','Font','FontSize'],
		['TextColor','BGColor'],
		['Maximize', 'ShowBlocks']
	];

	config.toolbar_Basic =
	[ ['Font','FontSize','Bold','Italic','Underline','Strike','TextColor','BGColor','Smiley'] ];

	config.toolbar_Empty = 
	[
	]
	

};
