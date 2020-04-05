from flask import Flask , render_template , request
import pyrebase

app = Flask(__name__)

config = {
    "apiKey": "AIzaSyAwVDfzCioSrav4IWIHs3r8dYDvuGmBfxk",
    "authDomain": "covid-19-27196.firebaseapp.com",
    "databaseURL": "https://covid-19-27196.firebaseio.com",
    "projectId": "covid-19-27196",
    "storageBucket": "covid-19-27196.appspot.com",
    "messagingSenderId": "671300410176",
    "appId": "1:671300410176:web:26890e0d395bc40f78b8a5",
    "measurementId": "G-QKJSHZ6SE4"
  }



firebase = pyrebase.initialize_app(config)

db = firebase.database()


auth = firebase.auth()
list1=[]
k=-1
k=k+1
@app.route('/',methods=['GET','POST'])

def index():
   
    return render_template('login.html')
   
@app.route('/corona',methods=['GET','POST'])
def corona():
   
   
    
    if request.method=='POST':
        name=request.form['name']
        print(name)
        list1.append(name)
        email = request.form['user']
        password=request.form['pass']
        try:
            auth.create_user_with_email_and_password(email,password)
        except:
            try:
                auth.sign_in_with_email_and_password(email,password)    
                return render_template('coronatic.html',name=name,p="Log In Successfuly")
            except:
                return render_template('login1.html',k="Wrong Eamil Or Password")    
    
   
    return render_template('coronatic.html',name=name,p="Sign In Successfuly")

@app.route('/COVID-19')    

def covid():
    print(list1[k])
    
    return render_template('index.html',name=list1[k])

@app.route('/Detail' , methods=['GET','POST'])

def detail():
    
    age_list=['Below 5','6 to 10','16 to 25','26 to 35','36 to 45','46 to 55','56 to 65','66 to 75','Above 75']
    gender_list=['Male','Female','Other']
    temp_list=['Normal 96-97','Fever 98 - 102','High Fever >102.6']
    Symptoms_list=['Dry Cough','Loss or Diminished Sense of Smell','Sore Throat','Weakness','Change in appetite','Feeling breathless','Drowsiness','Persistent Pain and pressure in chest']
    Travel_list=['No Travel History','No contact with anyone with symptoms','History of travel or meeting in affected area in last 14 days','Close Contact with confirmed COVID in last 14 days']
    Medical_list=['Diabetes',' High Blood Pressure','Heart Disease or lung disease',' Reduced immunity','None Of These']
    age=""
    gender=""
    temp=""
    symtoms=[]
    travel=""
    mediacl=[]
    if request.method=='POST':
        age=request.form.get('inlineAgeRadioOptions')
        gender=request.form.get('inlineGenderOptions')
        temp=request.form.get('inlineTempOptions')
        symtoms=request.form.getlist('symptoms')
        travel=request.form.get('travel')
        mediacl=request.form.getlist('medical')
        
    send_sym=[]
    sned_med=[]
    for i in range(0,len(symtoms)):
        send_sym.append(Symptoms_list[int(symtoms[i])])

    for i in range(0,len(mediacl)):
        sned_med.append(Medical_list[int(mediacl[i])])

    check="You are Not In Danger"
    if travel=='1':
        
        if temp =='0':
            if len(symtoms)==0 and len(mediacl)==0:
                check="Not In Danger"
            else:
                check="You are In Low Risk Stage , If you want to contact Applo then go for this given link (https://www.apollohospitals.com/contact-us) or Help line number 1860 500 0101"    
                    
        else:
            check="You are In High Risk Stage ,You Must  contact to Applo or go for this given link (https://www.apollohospitals.com/contact-us) or Help line number 1860 500 0101 ,If you Want Your Family safe"       
                    
                    
               

    if travel=='2' or travel =='3':
        if temp=='0':
            if len(symtoms)==0 and len(mediacl)==0:
                check="You are In Low Risk Stage , If you want to contact Applo then go for this given link (https://www.apollohospitals.com/contact-us) or Help line number 1860 500 0101"
            else:
                 check="You are In High Risk Stage ,You Must  contact to Applo or go for this given link (https://www.apollohospitals.com/contact-us) or Help line number 1860 500 0101 ,If you Want Your Family safe"       
                    
        else:       
             check="You are In High Risk Stage ,You Must  contact to Applo or go for this given link (https://www.apollohospitals.com/contact-us) or Help line number 1860 500 0101 ,If you Want Your Family safe"       

            
    print(send_sym,sned_med)           
                    

    return render_template('detail.html',age=age_list[int(age)],gender=gender_list[int(gender)],temp=temp_list[int(temp)],symptoms=send_sym,travel=Travel_list[int(travel)],medical=sned_med,name=list1[k],len_sy=len(send_sym),len_med=len(sned_med),check=check)


if __name__ == "__main__":
    app.run(debug=True)    