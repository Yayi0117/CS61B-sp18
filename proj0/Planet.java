public class Planet{
  public double xxPos;
  public double yyPos;
  public double xxVel;
  public double yyVel;
  public double mass;
  public String imgFileName;
    public Planet(double xP, double yP, double xV,
              double yV, double m, String img){
                xxPos = xP;
                yyPos = yP;
                xxVel = xV;
                yyVel = yV;
                mass = m;
                imgFileName = img;
              }
    public Planet(Planet p){
      xxPos = p.xxPos;
      yyPos = p.yyPos;
      xxVel = p.xxVel;
      yyVel = p.yyVel;
      mass = p.mass;
      imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p){
      double s = (p.xxPos-this.xxPos)*(p.xxPos-this.xxPos) + (p.yyPos-this.yyPos)*(p.yyPos-this.yyPos);
      double dis = Math.sqrt(s);      
      return dis;
    }

    public double calcForceExertedBy(Planet p){
      /** F=(G*m1*m2)/(r*r) */
      double G = 6.67e-11;
      return G*this.mass*p.mass/(calcDistance(p)*calcDistance(p));
    }

    public double calcForceExertedByX(Planet p){
      return calcForceExertedBy(p)*(p.xxPos-this.xxPos)/calcDistance(p);
    }

    public double calcForceExertedByY(Planet p){
      return calcForceExertedBy(p)*(p.yyPos-this.yyPos)/calcDistance(p);
    }

    public double calcNetForceExertedByX(Planet[] array){
      int len = array.length;
      double sum = 0;
      for (int i=0; i<len; i++){
        if (this.equals(array[i])){
          continue;
        }
        sum +=calcForceExertedByX(array[i]);
      }
      return sum;
    }

    public double calcNetForceExertedByY(Planet[] array){
      int len = array.length;
      double sum = 0;
      for (int i=0; i<len; i++){
        if (this.equals(array[i])){
          continue;
        }
        sum +=calcForceExertedByY(array[i]);
      }
      return sum;
    }

    public void update(double dt, double fX, double fY){
      double ax = fX/this.mass;
      double ay = fY/this.mass;
      this.xxVel = this.xxVel + ax*dt;
      this.yyVel = this.yyVel + ay*dt;
      this.xxPos = this.xxPos + this.xxVel*dt;
      this.yyPos = this.yyPos + this.yyVel*dt;      
    }

    public void draw(){
      StdDraw.picture(this.xxPos, this.yyPos, "images/" +this.imgFileName);
    }
}