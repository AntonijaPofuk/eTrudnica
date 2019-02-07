# -*- coding: utf-8 -*-
"""
Created on Thu Feb  7 21:14:17 2019

@author: antonija
"""


@app.route('/')
@app.route('/index')
def index():
    return "Hello, World!"