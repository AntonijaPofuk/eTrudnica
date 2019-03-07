from flask import Flask, request, flash, url_for, redirect, render_template
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy import create_engine

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'postgresql://postgres:admin123@localhost:5432/tbp_baza'
db = SQLAlchemy(app)
engine = create_engine('postgresql://postgres:admin123@localhost:5432/tbp_baza')
connection = engine.connect()

#stmt = 'SELECT * FROM bolnicki_podaci'
#result_proxy = connection.execute(stmt)
#results = result_proxy.fetchall()

class Book(db.Model):
    title = db.Column(db.String(80), unique=True, nullable=False)
    id=db.Column(db.Integer, unique=True, nullable=False, primary_key=True)
    def __repr__(self):
        return "<Title: {}>".format(self.title)
class Korisnica(db.Model):
    OIB = db.Column(db.String(11), unique=True, nullable=False, primary_key=True)
    ime=db.Column(db.Text,nullable=False)
    prezime=db.Column(db.Text,nullable=False)
    adresa = db.Column(db.Text,nullable=False)
    email=db.Column(db.Text,nullable=True)
    telefon=db.Column(db.Text,nullable=True)
    datum_rodjenja=db.Column(db.Text,nullable=False)
    def __repr__(self):
        return "<Title: {}>".format(self.OIB)
class Trudnoca(db.Model):
    id_trudnoce = db.Column(db.String(1), unique=True, nullable=False, primary_key=True)
    id_korisnice=db.Column(db.String(11),unique=True, nullable=False, primary_key=True)
    pocetak=db.Column(db.Text,nullable=False)
    kraj = db.Column(db.Text,nullable=False)
    opis=db.Column(db.Text,nullable=True)
    def __repr__(self):
        return "<Title: {}>".format(self.id_trudnoce)
class Novorodence(db.Model):
    id_novorodence = db.Column(db.String(1), unique=True, nullable=False, primary_key=True)
    id_trudnoce=db.Column(db.String(1),unique=True, nullable=False, primary_key=True)
    ime=db.Column(db.Text,nullable=False)
    spol = db.Column(db.Text,nullable=False)
    tezina=db.Column(db.Text,nullable=True)
    def __repr__(self):
        return "<Title: {}>".format(self.id_novorodence)
class Bolnicki_podaci(db.Model):
    id_bolnicki_podaci = db.Column(db.String(1), unique=True, nullable=False, primary_key=True)
    doktor=db.Column(db.String(1),unique=True, nullable=False, primary_key=True)
    naziv_bolnice=db.Column(db.Text,nullable=False)
    podaci = db.Column(db.Text,nullable=False)
    def __repr__(self):
        return "<Title: {}>".format(self.id_bolnicki_podaci)
def sql(rawSql, sqlVars={}):
    "Execute raw sql, optionally with prepared query"
    assert type(rawSql)==str
    assert type(sqlVars)==dict
    res=db.session.execute(rawSql, sqlVars)
    db.session.commit()
    return res
@app.route("/forward/", methods=['POST'])
def move_forward():
    return render_template('updateTrudnoca.html')
#@app.before_first_request
#def initDBforFlask():
# sql("CREATE TABLE IF NOT EXISTS animals (id SERIAL PRIMARY KEY, name VARCHAR(160) UNIQUE);")
# sql("INSERT INTO animals(name) VALUES ('Lion') ON CONFLICT (name) DO NOTHING;")
# sql("INSERT INTO animals(name) VALUES ('Dragon') ON CONFLICT (name) DO NOTHING;")
# sql("INSERT INTO animals(name) VALUES ('TeroKarvinen.com') ON CONFLICT (name) DO NOTHING;")
# sql("INSERT INTO animals(name) VALUES ('Horse') ON CONFLICT (name) DO NOTHING;")
@app.route("/home", methods=["GET", "POST"])
def home():
    korisnice=None
    if request.form:
        try:
            korisnica = Korisnica(OIB=request.form.get("OIB"),
                                  ime=request.form.get("ime"),
                                  prezime=request.form.get("prezime"),
                                  adresa=request.form.get("adresa"),
                                  email=request.form.get("email"),
                                  telefon=request.form.get("telefon"),
                                  datum_rodjenja=request.form.get("datum_rodjenja"))
            db.session.add(korisnica)
            db.session.commit()
        except Exception as e:
            print("Oops, pogreška se dogodila, pokušajte ponovno")
            print(e)
    korisnice = Korisnica.query.all()
    
    trudnoce=sql("SELECT * FROM trudnoca;")
    novorodencad=sql("SELECT * FROM NOVORODENCE;")
    doktori=sql("SELECT * FROM bolnicki_podaci;")


    return render_template("home.html", korisnice=korisnice,trudnoce=trudnoce,novorodencad=novorodencad,doktori=doktori)

@app.route("/modifyTrudnoca", methods=["GET", "POST"])
def modifyTrudnoca():
    trudnoce=None
    if request.form:
        try:
             trudnoca = Trudnoca(id_trudnoce=request.form.get("id_trudnoce"),
                            id_korisnice=request.form.get("id_korisnice"),
                            pocetak=request.form.get("pocetak"),
                            kraj=request.form.get("kraj"),
                            opis=request.form.get("opis"))
             db.session.add(trudnoca)
             db.session.commit()
        except Exception as e:
             print("Oops, pogreška se dogodila, pokušajte ponovno")
             print(e)
    trudnoce = Trudnoca.query.all()
    return render_template("updateTrudnoca.html",trudnoce=trudnoce)

@app.route("/modifyNovorodence", methods=["GET", "POST"])
def modifyNovorodence():
    novorodencad=None
    if request.form:
        try:
             novorodence = Novorodence(id_novorodence=request.form.get("id_novorodence"),
                      id_trudnoce=request.form.get("id_trudnoce"),
                      ime=request.form.get("ime"),
                      spol=request.form.get("spol"),
                      tezina=request.form.get("tezina"))
             db.session.add(novorodence)
             db.session.commit()
        except Exception as e:
             print("Oops, pogreška se dogodila, pokušajte ponovno")
             print(e)
    novorodencad = Novorodence.query.all()
    return render_template("updateNovorodence.html",novorodencad=novorodencad)
@app.route("/modifyBolnickiPodaci", methods=["GET", "POST"])
def modifyBolnickiPodaci():
    bolnicki_podaci2=None
    if request.form:
        try:
             bolnicki_podaci = Bolnicki_podaci(id_bolnicki_podaci=request.form.get("id_bolnicki_podaci"),
                      doktor=request.form.get("doktor"),
                      naziv_bolnice=request.form.get("naziv_bolnice"),
                      podaci=request.form.get("podaci"))
             db.session.add(bolnicki_podaci)
             db.session.commit()
        except Exception as e:
             print("Oops, pogreška se dogodila, pokušajte ponovno")
             print(e)
    bolnicki_podaci2 = Bolnicki_podaci.query.all()
    return render_template("updateNovorodence.html",bolnicki_podaci2=bolnicki_podaci2)
#@app.route("/doctors")
#def doctors():
#    doktori=sql("SELECT * FROM bolnicki_podaci;")
#    return render_template("doctors.html", doktori=doktori)

@app.route("/new", methods=["GET", "POST"])
def new():
    books=None
    if request.form:
        try:
            book = Book(title=request.form.get("title"),id=request.form.get("id"))
            db.session.add(book)
            db.session.commit()
        except Exception as e:
            print("Oops, pogreška se dogodila, pokušajte ponovno")
            print(e)
    books = Book.query.all()
    return render_template("new.html", books=books)
@app.route("/update", methods=["POST"])
def update():
    try:
        newtitle = request.form.get("newtitle")
        oldtitle = request.form.get("oldtitle")
        newid = request.form.get("newid")
        oldid = request.form.get("oldid")
        book = Book.query.filter_by(id=oldid).first()
        book.id = newid
        book.title = newtitle
        db.session.commit()
    except Exception as e:
        print("Oops, pogreška se dogodila, pokušajte ponovno")
        print(e)
    return redirect("/new")
@app.route("/updateKorisnica", methods=["GET", "POST"])
def updateKorisnica():
    try:
        newOIB = request.form.get("newOIB")
        oldOIB = request.form.get("oldOIB")
        newime = request.form.get("newime")
        oldime = request.form.get("oldime")
        newprezime = request.form.get("newprezime")
        oldprezime = request.form.get("oldprezime")
        newadresa = request.form.get("newadresa")
        oldadresa = request.form.get("oldadresa")
        newmail = request.form.get("newmail")
        oldmail = request.form.get("oldmail")
        newtel = request.form.get("newtel")
        oldtel = request.form.get("oldtel")
        newdatum_rodjenja = request.form.get("newdatum_rodjenja")
        olddatum_rodjenja = request.form.get("olddatum_rodjenja")
        korisnica = Korisnica.query.filter_by(OIB=oldOIB).first()
        korisnica.OIB = newOIB
        korisnica.ime = newime
        korisnica.prezime = newprezime
        korisnica.adresa = newadresa
        korisnica.email = newmail
        korisnica.telefon = newtel
        korisnica.datum_rodjenja = newdatum_rodjenja
        db.session.commit()
    except Exception as e:
        print("Oops, pogreška se dogodila, pokušajte ponovno")
        print(e)
    return redirect("/home")
@app.route("/updateTrudnoca", methods=["GET","POST"])
def updateTrudnoca():
     try:
         newid_trudnoce = request.form.get("newid_trudnoce")
         oldid_trudnoce = request.form.get("oldid_trudnoce")
         newid_korisnice = request.form.get("newid_korisnice")
         oldid_korisnice = request.form.get("oldid_korisnice")
         newpocetak = request.form.get("newpocetak")
         oldpocetak = request.form.get("oldpocetak")
         newkraj = request.form.get("newkraj")
         oldkraj = request.form.get("oldkraj")
         newopis = request.form.get("newopis")
         oldopis = request.form.get("oldopis")
         trudnoca = Trudnoca.query.filter_by(id_trudnoce=oldid_trudnoce).first()
         trudnoca.id_trudnoce = newid_trudnoce
         trudnoca.id_korisnice = newid_korisnice
         trudnoca.pocetak = newpocetak
         trudnoca.kraj = newkraj
         trudnoca.opis = newopis
         db.session.commit()
     except Exception as e:
        print("Oops, pogreška se dogodila, pokušajte ponovno")
        print(e)
     return redirect("/modifyTrudnoca")
@app.route("/updateNovorodence", methods=["POST"])
def updateNovorodence():
    try:
        newid_novorodence = request.form.get("newid_novorodence")
        odlid_novorodence = request.form.get("oldid_novorodence")
        newime = request.form.get("newime")
        oldime = request.form.get("oldime")
        newid_trudnoce = request.form.get("newid_trudnoce")
        oldid_trudnoce = request.form.get("oldid_trudnoce")
        newspol = request.form.get("newspol")
        oldspol = request.form.get("oldspol")
        newtezina = request.form.get("newtezina")
        oldtezina = request.form.get("oldtezina")
        novorodence = Novorodence.query.filter_by(id_novorodence=odlid_novorodence).first()
        novorodence.id_novorodence = newid_novorodence
        novorodence.ime = newime
        novorodence.id_trudnoce = newid_trudnoce
        novorodence.spol = newspol
        novorodence.tezina = newtezina            
        db.session.commit()
    except Exception as e:
        print("Oops, pogreška se dogodila, pokušajte ponovno")
        print(e)
    return redirect("/modifyNovorodence")
@app.route("/updateBolnickiPodaci", methods=["POST"])
def updateBolnickiPodaci():
    try:
        newid_bolnicki_podaci = request.form.get("newid_bolnicki_podaci")
        odlid_bolnicki_podaci = request.form.get("oldid_bolnicki_podaci")
        newdoktor = request.form.get("newdoktor")
        olddoktor = request.form.get("olddoktor")
        newnaziv_bolnice = request.form.get("newnaziv_bolnice")
        oldnaziv_bolnice = request.form.get("oldnaziv_bolnice")
        newpodaci = request.form.get("newpodaci")
        oldpodaci = request.form.get("oldpodaci")
       
        bolnicki_podaci = Bolnicki_podaci.query.filter_by(id_bolnicki_podaci=odlid_bolnicki_podaci).first()
        bolnicki_podaci.id_bolnicki_podaci = newid_bolnicki_podaci
        bolnicki_podaci.doktor = newdoktor
        bolnicki_podaci.naziv_bolnice = newnaziv_bolnice
        bolnicki_podaci.podaci = newpodaci
        db.session.commit()
    except Exception as e:
        print("Oops, pogreška se dogodila, pokušajte ponovno")
        print(e)
    return redirect("/modifyBolnickiPodaci")
@app.route("/delete", methods=["POST"])
def delete():
    title = request.form.get("title")
    book = Book.query.filter_by(title=title).first()
    db.session.delete(book)
    db.session.commit()
    return redirect("/new")
#@app.route("/new", methods = ['GET', 'POST'])
#def new():
#   if request.method == 'POST':
#      if not request.form['name'] or not request.form['id']:
#         flash('Please enter all the fields', 'error')
#      else:
#         trudnica = User(request.form['name'], request.form['id'])
#         #         db.session.add(trudnica)
#         db.session.commit()
#         #         flash('Record was successfully added')
#         return redirect(url_for('show_all'))
#   return render_template("new.html")
if __name__ == "__main__":
       db.create_all() #kreira nove tablice
       app.run(debug=True)