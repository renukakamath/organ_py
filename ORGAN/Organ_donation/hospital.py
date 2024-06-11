
from flask import *
from database import *

import uuid

import json
from web3 import Web3, HTTPProvider

# truffle development blockchain address
blockchain_address = ' http://127.0.0.1:9545'
# Client instance to interact with the blockchain
web3 = Web3(HTTPProvider(blockchain_address))
# Set the default account (so we don't need to set the "from" for every transaction call)
web3.eth.defaultAccount = web3.eth.accounts[0]
compiled_contract_path = 'C:/Users/renuk/OneDrive/Desktop/ORGAN/ORGAN/Organ_donation/node_modules/.bin/build/contracts/organ.json'
# compiled_contract_path = 'F:/NGO/node_modules/.bin/build/contracts/medicines.json'
# Deployed contract address (see `migrate` command output: `contract address`)
deployed_contract_address = '0x9Ce02E8285c303c25bd5eAC766b5be386306259F'


hospital=Blueprint('hospital',__name__)

@hospital.route('/hospital_home')
def hospital_home():
	return render_template('hospital_home.html')


@hospital.route('/hospital_edit_profile',methods=['get','post'])
def hospital_edit_profile():
	data={}
	hid=session['hid']
	q="select * from hospital where hospital_id='%s'"%(hid)
	data['dir']=select(q)

	if 'update' in request.form:
		name=request.form['name']
		place=request.form['pl']
		phone=request.form['ph']
		email=request.form['em']
		q="update hospital set hospital_name='%s',place='%s',phone='%s',email='%s' where hospital_id='%s'"%(name,place,phone,email,hid)
		update(q)
		flash("updated")
		return redirect(url_for('hospital.hospital_edit_profile'))
	return render_template("hospital_edit_profile.html",data=data)
	if action=='update':
		q="select * from hospital where login_id='%s'"%(id)
		data['dir']=select(q)

	if 'update' in request.form:
		name=request.form['name']
		place=request.form['pl']
		lat=request.form['lat']
		longi=request.form['long']
		phone=request.form['ph']
		email=request.form['em']
		q="update hospital set hospital_name='%s',place='%s',latitude='%s',longitude='%s',phone='%s',email='%s' where login_id='%s'"%(name,place,lat,longi,phone,email,id)
		update(q)
		flash("updated")
		return redirect(url_for('admin.admin_manage_hospital'))
	return render_template("admin_manage_hospital.html",data=data)


@hospital.route('/hospital_view_donation_request')
def hospital_view_donation_request():
	data={}
	hid=session['hid']
	q="select * from donation inner join user USING(user_id)"
	res=select(q)
	data['dn']=res

	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
	else:
		action=None

	if action=='accept':#blockchain
		q="insert into request values(null,'%s','%s','Accepted','donation')"%(id,hid)
		insert(q)
		q="update donation set status='Accepted' where donation_id='%s'"%(id)
		update(q)


		import datetime
		d=datetime.datetime.now().strftime("%d-%m-%Y %H:%M:%S")
		with open(compiled_contract_path) as file:
			contract_json = json.load(file)  # load contract info as JSON
			contract_abi = contract_json['abi']  # fetch contract's abi - necessary to call its functions
			contract = web3.eth.contract(address=deployed_contract_address, abi=contract_abi)
			id=web3.eth.get_block_number()
		message = contract.functions.add_request(id,int(id),int(hid),'Accepted','donation').transact()






		flash("Accepted")
		return redirect(url_for('hospital.hospital_view_donation_request'))

	if action=='reject':
		q="update donation set status='Rejected' where donation_id='%s'"%(id)
		update(q)


		import datetime
		d=datetime.datetime.now().strftime("%d-%m-%Y %H:%M:%S")
		with open(compiled_contract_path) as file:
			contract_json = json.load(file)  # load contract info as JSON
			contract_abi = contract_json['abi']  # fetch contract's abi - necessary to call its functions
			contract = web3.eth.contract(address=deployed_contract_address, abi=contract_abi)
			id=web3.eth.get_block_number()
		message = contract.functions.add_request(id,int(id),int(hid),'Rejected','donation').transact()
		flash("Rejected")
		return redirect(url_for('hospital.hospital_view_donation_request'))
	return render_template('hospital_view_donation_request.html',data=data)



@hospital.route('/hospital_chat_user',methods=['get','post'])
def hospital_chat_user():
	data={}
	lid=session['lid']
	uid=request.args['id']
	data['lid']=lid
	if 'submit' in request.form:
		msg=request.form['msg']
		q="INSERT INTO `chat` VALUES(NULL,'%s','%s','%s',NOW())"%(lid,uid,msg)
		insert(q)
		return redirect(url_for('hospital.hospital_chat_user',id=uid))

	q="SELECT * FROM `chat` where (sender_id='%s' and receiver_id='%s') or (sender_id='%s' and receiver_id='%s')" %(lid,uid,uid,lid)
	res=select(q)
	data['chat']=res
	return render_template("hospital_chat_user.html",data=data)



@hospital.route('/hospital_view_post_request',methods=['get','post'])
def hospital_view_post_request():
	data={}
	hid=session['hid']
	q="SELECT * FROM `post` INNER JOIN `user` USING(user_id)"
	res=select(q)
	data['dn']=res

	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
	else:
		action=None

	if action=='accept':
		q="update post set status='accepted' where post_id='%s'"%(id)
		update(q)
		# q="insert into request values(null,'%s','%s','accepted')"%(id,hid)
		# insert(q)
		flash('accepted')
		return redirect(url_for('hospital.hospital_view_post_request'))

	if action=='reject':
		q="update post set status='rejected' where post_id='%s'"%(id)
		update(q)
		flash('rejected')
		return redirect(url_for('hospital.hospital_view_post_request'))

	if 'submit' in request.form:
		title=request.form['title']
		oraganname=request.form['oraganname']
		description=request.form['description']
		q="INSERT INTO `post` VALUES(NULL,'%s','%s','%s','%s','pending',CURDATE(),'pending','hospital')"%(session['hid'],title,oraganname,description)
		insert(q)
	return render_template('hospital_view_post_request.html',data=data)


@hospital.route('/hospital_view_requested_user_details')
def hospital_view_requested_user_details():
	data={}
	id=request.args['id']
	q="SELECT * FROM `user` where user_id='%s'"%(id)
	res=select(q)
	data['us']=res
	return render_template('hospital_view_requested_user_details.html',data=data)



@hospital.route('/hospital_chat_requested_user',methods=['get','post'])
def hospital_chat_requested_user():
	data={}
	lid=session['lid']
	uid=request.args['id']
	data['lid']=lid
	if 'submit' in request.form:
		msg=request.form['msg']
		q="INSERT INTO `chat` VALUES(NULL,'%s','%s','%s',NOW())"%(lid,uid,msg)
		insert(q)
		return redirect(url_for('hospital.hospital_chat_requested_user',id=uid))

	q="SELECT * FROM `chat` where (sender_id='%s' and receiver_id='%s') or (sender_id='%s' and receiver_id='%s')" %(lid,uid,uid,lid)
	res=select(q)
	data['chat']=res
	return render_template("hospital_chat_requested_user.html",data=data)




@hospital.route('/hospital_send_operation_date',methods=['get','post'])
def hospital_send_operation_date():
	data={}
	hid=session['hid']
	pid=request.args['pid']
	if 'send' in request.form:
		dt=request.form['dt']
		q="INSERT INTO `request` VALUES(NULL,'%s','%s','accepted','post')"%(pid,hid)
		insert(q)
		q="update post set operation_date='%s' where post_id='%s'"%(dt,pid)
		update(q)
		return redirect(url_for('hospital.hospital_view_post_request'))
	return render_template('hospital_send_operation_date.html',data=data)