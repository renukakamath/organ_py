from flask import *
from database import *
import uuid

public=Blueprint('public',__name__)

@public.route('/')
def home():
	return render_template('home.html')

@public.route('/login',methods=['get','post'])
def login():

	if 'submit' in request.form:
		uname=request.form['uname']
		pwd=request.form['pwd']
		q="SELECT * FROM `login` WHERE `username`='%s' AND `password`='%s'"%(uname,pwd)
		res=select(q)
		if res:
			session['lid']=res[0]['login_id']
			if res[0]['usertype']=='admin':
				flash("login successfully....!")
				return redirect(url_for('admin.admin_home'))
			elif res[0]['usertype']=='hospital':
				q="select * from hospital where login_id='%s'"%(session['lid'])
				res=select(q)
				session['hid']=res[0]['hospital_id']
				flash("login successfully....!")
				return redirect(url_for('hospital.hospital_home'))
			elif res[0]['usertype']=='user':
				q="select * from user where login_id='%s'"%(session['lid'])
				res=select(q)
				session['uid']=res[0]['user_id']
				flash("login successfully....!")
				return redirect(url_for('user.userhome'))
		else:
			flash("INVALID USERNAME OR PASSWORD")
	return render_template('login.html')


@public.route('/hospital_register',methods=['get','post'])
def hospital_register():
	if 'manage' in request.form:
		name=request.form['name']
		place=request.form['pl']
		phone=request.form['ph']
		email=request.form['em']
		uname=request.form['uname']
		password=request.form['pass']
		q="select * from login where username='%s' and password='%s'"%(uname,password)
		res=select(q)
		if res:
			flash('THIS USERNAME AND PASSWORD IS ALREADY EXIST')
		else:
			q="insert into login values(NULL,'%s','%s','pending')"%(uname,password)
			lid=insert(q)
			q="insert into hospital values(NULL,'%s','%s','%s','%s','%s')"%(lid,name,place,phone,email)
			insert(q)
			flash("Registered Successfully...!")
		return redirect(url_for('public.hospital_register'))

	return render_template("hospital_register.html")

@public.route('/userreg',methods=['get','post'])
def userreg():
	if 'manage' in request.form:
		fn=request.form['fi']
		ln=request.form['la']
		pl=request.form['pl']
		ph=request.form['ph']
		em=request.form['em']
		un=request.form['us']
		pas=request.form['pa']
		q="select * from login where username='%s' and password='%s'"%(un,pas)
		res=select(q)
		if res:
			flash('THIS USERNAME AND PASSWORD IS ALREADY EXIST')
		else:
			q="insert into login values(NULL,'%s','%s','user')"%(un,pas)
			lid=insert(q)
			q="insert into user values(NULL,'%s','%s','%s','%s','%s','%s')"%(lid,fn,ln,pl,ph,em)
			insert(q)
			flash("Registered Successfully...!")
		return redirect(url_for('public.userreg'))

	return render_template("userreg.html")

