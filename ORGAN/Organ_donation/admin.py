from public import *


admin=Blueprint('admin',__name__)

@admin.route('/admin_home')
def admin_home():
	return render_template('admin_home.html')

@admin.route('/admin_view_hospital')
def admin_view_hospital():
	data={}
	q="select * from hospital inner join login using(login_id)"
	res=select(q)
	data['hos']=res

	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
	else:
		action=None


	if action=='accept':
		q="update login set usertype='hospital' where login_id='%s'"%(id)
		update(q)
		flash('accepted')
		return redirect(url_for('admin.admin_view_hospital'))

	if action=='reject':
		q="update login set usertype='hospitalss' where login_id='%s'"%(id)
		update(q)
		flash('rejected')
		return redirect(url_for('admin.admin_view_hospital'))
	return render_template('admin_view_hospital.html',data=data)



@admin.route('/admin_view_users')
def admin_view_users():
	data={}
	q="select * from user"
	res=select(q)
	data['us']=res
	return render_template('admin_view_users.html',data=data)


@admin.route('/admin_view_post_status')
def admin_view_post_status():
	data={}
	q="select * from post"
	res=select(q)
	data['ps']=res
	return render_template('admin_view_post_status.html',data=data)



@admin.route('/admin_view_donation')
def admin_view_donation():
	data={}
	q="select * from donation inner join user USING(user_id)"
	res=select(q)
	data['dn']=res
	return render_template('admin_view_donation.html',data=data)



@admin.route('/admin_view_complaint_send_reply',methods=['get','post'])
def admin_view_complaint_send_reply():
	data={}
	q="SELECT *,CONCAT(`first_name`,' ',`last_name`) AS `name` FROM `complaint` INNER JOIN `user` USING(`user_id`)"
	res=select(q)
	data['complaints']=res

	j=0
	for i in range(1,len(res)+1):
		print('submit'+str(i))
		if 'submit'+str(i) in request.form:
			reply=request.form['reply'+str(i)]
			print(reply)
			print(j)
			print(res[j]['complaint_id'])
			q="update complaint set reply='%s' where complaint_id='%s'" %(reply,res[j]['complaint_id'])
			print(q)
			update(q)
			flash("success")
			return redirect(url_for('admin.admin_view_complaint_send_reply')) 	
		j=j+1
	return render_template('admin_view_complaint_send_reply.html',data=data)
