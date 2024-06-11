from flask import Flask 
from public import public
from admin import admin
from hospital import hospital
from api import api
from user import user


app=Flask(__name__)
app.secret_key="key"

app.register_blueprint(public)
app.register_blueprint(admin,url_prefix='/admin')
app.register_blueprint(hospital,url_prefix='/hospital')
app.register_blueprint(api,url_prefix='/api')
app.register_blueprint(user,url_prefix='/user')

app.run(debug=True,port=5051,host="0.0.0.0")