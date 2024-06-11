from flask import *
from database import *
import demjson
import uuid

api=Blueprint('api',__name__)

@api.route('/login')
def login():
	data={}
	username=request.args['username']
	password=request.args['password']
	q="select * from login where username='%s' and password='%s'"%(username,password)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return demjson.encode(data)

@api.route('/usermanagecomplaints')
def usermanagecomplaints():
	data={}
	lid=request.args['lid']
	complaint=request.args['complaint']
	q="insert into complaint values(null,(select user_id from user where login_id ='%s'),'%s','pending',curdate())"%(lid,complaint)
	insert(q)
	data['status']="success"
	data['method']="usermanagecomplaints"
	return demjson.encode(data)


@api.route('/userviewcomplaints')
def userviewcomplaints():
	data={}
	lid=request.args['lid']
	q="select * from complaint where user_id=(select user_id from user where login_id ='%s')"%(lid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="userviewcomplaints"
	return demjson.encode(data)


@api.route('/userregister')
def userregister():
	data={}
	fname=request.args['fname']
	lname=request.args['lname']
	place=request.args['place']
	email=request.args['email']
	phone=request.args['phone']
	username=request.args['username']
	password=request.args['password']
	q="select * from login where username='%s'"%(username)
	res=select(q)
	if res:
		data['status']="duplicate"
	else:
		q="insert into login values(null,'%s','%s','user')"%(username,password)
		id=insert(q)
		q="insert into user values(null,'%s','%s','%s','%s','%s','%s')"%(id,fname,lname,place,phone,email)
		insert(q)
		data['status']="success"
	return demjson.encode(data)


@api.route('/userviewhospitals')
def userviewhospitals():
	data={}
	q="select * from hospital"
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return demjson.encode(data)



@api.route('/usersenddonationrequest')
def usersenddonationrequest():
	data={}
	lid=request.args['lid']
	title=request.args['title']
	organ=request.args['organ']
	q="insert into donation values(null,(select user_id from user where login_id ='%s'),'%s','%s',curdate(),'pending')"%(lid,title,organ)
	insert(q)
	data['status']="success"
	data['method']="usersenddonationrequest"
	return demjson.encode(data)


@api.route('/userviewdonationrequest')
def userviewdonationrequest():
	data={}
	lid=request.args['lid']
	q="select * from donation where user_id=(select user_id from user where login_id ='%s')"%(lid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="userviewdonationrequest"
	return demjson.encode(data)


@api.route('/chat')
def chat():
	data={}
	sender_id=request.args['sender_id']
	receiver_id=request.args['receiver_id']
	details=request.args['details']
	q="insert into chat values(null,'%s','%s','%s',curdate())" %(sender_id,receiver_id,details)
	insert(q)
	data['status']="success"
	data['method']="chat"
	return demjson.encode(data)


@api.route('/chatdetail')
def chatdetail():
	data={}
	sender_id=request.args['sender_id']
	receiver_id=request.args['receiver_id']
	q="SELECT * FROM chat WHERE (sender_id='%s' AND receiver_id='%s') or (sender_id='%s' AND receiver_id='%s')" %(sender_id,receiver_id,receiver_id,sender_id)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="chatdetail"
	return demjson.encode(data)


@api.route('/userviewothersdonationrequest')
def userviewothersdonationrequest():
	data={}
	lid=request.args['lid']
	q="select * from donation inner join user using(user_id) where user_id!=(select user_id from user where login_id ='%s')"%(lid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return demjson.encode(data)


@api.route('/usersendpost')
def usersendpost():
	data={}
	lid=request.args['lid']
	title=request.args['title']
	organ=request.args['organ']
	description=request.args['description']
	q="insert into post values(null,(select user_id from user where login_id ='%s'),'%s','%s','0','%s',curdate(),'pending')"%(lid,title,organ,description)
	insert(q)
	data['status']="success"
	data['method']="usersendpost"
	return demjson.encode(data)


@api.route('/userviewpost')
def userviewpost():
	data={}
	lid=request.args['lid']
	q="select * from post where user_id=(select user_id from user where login_id ='%s')"%(lid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="userviewpost"
	return demjson.encode(data)



@api.route('/userviewotherspostrequest')
def userviewotherspostrequest():
	data={}
	lid=request.args['lid']
	q="select * from post inner join user using(user_id) where user_id!=(select user_id from user where login_id ='%s')"%(lid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="userviewotherspostrequest"
	return demjson.encode(data)


@api.route('/userviewpoststatus')
def userviewpoststatus():
	data={}
	pid=request.args['pid']
	q="SELECT * FROM `request` INNER JOIN `hospital` USING(hospital_id) where sender_id='%s' and type='post'"%(pid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return demjson.encode(data)

@api.route('/userviewdonationstatus')
def userviewdonationstatus():
	data={}
	did=request.args['did']
	q="SELECT * FROM `request` INNER JOIN `hospital` USING(hospital_id) where sender_id='%s' and type='donation'"%(did)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return demjson.encode(data)

@api.route('/accept')
def accept():
	data={}
	post_ids=request.args['post_ids']
	q="UPDATE `post` SET `status`='accepted' WHERE `post_id`='%s'"%(post_ids)
	res=update(q)
	if res:
		data['status']="success"
	else:
		data['status']="failed"
	data['method']="accept"		
	return demjson.encode(data)

@api.route('/rejects')
def rejects():
	data={}
	pos_id=request.args['pos_id']
	q="UPDATE `post` SET `status`='rejected' WHERE `post_id`='%s'"%(post_ids)
	res=update(q)
	if res:
		data['status']="success"
	else:
		data['status']="failed"
	data['method']="reject"		
	return demjson.encode(data)