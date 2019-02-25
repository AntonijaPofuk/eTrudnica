from flask import Flask, request, flash, url_for, redirect, render_template
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy import create_engine


app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'postgresql://postgres:admin123@localhost:5432/tbp_baza'

db = SQLAlchemy(app)

engine = create_engine('postgresql://postgres:admin123@localhost:5432/tbp_baza')
connection = engine.connect()
stmt = 'SELECT * FROM bolnicki_podaci'
result_proxy = connection.execute(stmt)
results = result_proxy.fetchall()


class Book(db.Model):
    title = db.Column(db.String(80), unique=True, nullable=False)
    id=db.Column(db.Integer, unique=True, nullable=False, primary_key=True)
    def __repr__(self):
        return "<Title: {}>".format(self.title)
    

def sql(rawSql, sqlVars={}):
    "Execute raw sql, optionally with prepared query"
    assert type(rawSql)==str
    assert type(sqlVars)==dict
    res=db.session.execute(rawSql, sqlVars)
    db.session.commit()
    return res

#@app.before_first_request
#def initDBforFlask():
# sql("CREATE TABLE IF NOT EXISTS animals (id SERIAL PRIMARY KEY, name VARCHAR(160) UNIQUE);")
# sql("INSERT INTO animals(name) VALUES ('Lion') ON CONFLICT (name) DO NOTHING;")
# sql("INSERT INTO animals(name) VALUES ('Dragon') ON CONFLICT (name) DO NOTHING;")
# sql("INSERT INTO animals(name) VALUES ('TeroKarvinen.com') ON CONFLICT (name) DO NOTHING;")
# sql("INSERT INTO animals(name) VALUES ('Horse') ON CONFLICT (name) DO NOTHING;")
 

@app.route("/")
def home():
    return render_template("home.html")

@app.route("/doctors")
def doctors():
    doktori=sql("SELECT * FROM bolnicki_podaci;")
    return render_template("doctors.html", doktori=doktori)


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
        book = Book.query.filter_by(title=oldtitle).first()
        book.title = newtitle
        db.session.commit()
    except Exception as e:
        print("Oops, pogreška se dogodila, pokušajte ponovno")
        print(e)
    return redirect("/new")

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
#         
#         db.session.add(trudnica)
#         db.session.commit()
#         
#         flash('Record was successfully added')
#         return redirect(url_for('show_all'))
#   return render_template("new.html")

if __name__ == "__main__":
       db.create_all() #kreira nove tablice

       app.run(debug=True)
