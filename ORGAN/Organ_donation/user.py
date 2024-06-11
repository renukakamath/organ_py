from flask import *
from database import *
import uuid

user=Blueprint('user',__name__)

@user.route('/userhome')
def userhome():
	return render_template('userhome.html')

@user.route('/user_view_hospitals')
def user_view_hospitals():
	data={}
	q="select * from hospital inner join login using(login_id)"
	res=select(q)
	data['hos']=res
	return render_template('user_view_hospitals.html',data=data)


@user.route('/user_post_request',methods=['get','post'])
def user_post_request():
	data={}
	# hid=session['hid']
	# pid=request.args['pid']
	q="select * from post"
	res=select(q)
	data['ps']=res
	if 'send' in request.form:
		title=request.form['title']
		oname=request.form['oname']
		des=request.form['des']
		# dt=request.form['dt']
		q="INSERT INTO `post` VALUES(NULL,'%s','%s','%s','%s','pending',curdate(),'pending')"%(session['uid'],title,oname,des)
		insert(q)
		return redirect(url_for('user.user_post_request'))
	return render_template('user_post_request.html',data=data)


@user.route('/user_send_complaint',methods=['get','post'])
def user_send_complaint():
	data={}
	# hid=session['hid']
	# pid=request.args['pid']
	q="select * from complaint"
	res=select(q)
	data['ps']=res
	if 'send' in request.form:
		title=request.form['title']
		# oname=request.form['oname']
		# des=request.form['des']
		# dt=request.form['dt']
		q="INSERT INTO `complaint` VALUES(NULL,'%s','%s','pending',curdate())"%(session['uid'],title)
		insert(q)
		return redirect(url_for('user.user_send_complaint'))
	return render_template('user_send_complaint.html',data=data)


@user.route('/user_request',methods=['get','post'])
def user_request():
	data={}
	q="select * from hospital"
	res=select(q)
	data['hos']=res
	if 'send' in request.form:
		title=request.form['title']
		oname=request.form['oname']
		q="INSERT INTO `donation` VALUES(NULL,'%s','%s','%s',curdate(),'pending')"%(session['uid'],title,oname)
		insert(q)
		return redirect(url_for('user.user_request'))
	q="select * from donation where user_id='%s'"%(session['uid'])
	res=select(q)
	data['ps']=res
	return render_template('user_request.html',data=data)


@user.route('/user_view_others_request',methods=['get','post'])
def user_view_others_requestuser_view_others_request():
	data={}
	q="select * from donation inner join user using(user_id) where user_id<>'%s'"%(session['uid'])
	res=select(q)
	data['ps']=res
	return render_template('user_view_others_request.html',data=data)

@user.route('/user_chat_others',methods=['get','post'])
def user_chat_others():
    data={}
    uid=session['uid']
    did=request.args['did']
    if 'btn' in request.form:
        name=request.form['txt']
    
        q="insert into chat values(NULL,'%s','%s','%s',now())"%(uid,did,name)
        insert(q)
        return redirect(url_for("user.user_chat_others",did=did))
    q="SELECT * FROM chat WHERE (sender_id='%s' AND receiver_id='%s') OR (sender_id='%s' AND receiver_id=('%s')) order by chat_id"%(uid,did,did,uid)
    # q="select * from chats where senderid='%s' and receiverid=( select login_id from doctors where doctor_id='%s' )"%(uid,did)
    print(q)
    res=select(q)
    data['ress']=res
    return render_template('user_chat_others.html',data=data,uid=uid)