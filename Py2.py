import random

def nameRandom():
  name = ""
  rand = random.randint(3,10)
  
  for i in range(rand):
    name += chr(random.randint(65,122))
  return name

def dateRandom():
  year = random.randint(1000,9999)
  month = random.randint(1,12)
  day = 0
  if(month < 8):
    if (month % 2 == 1):
      day = random.randint(1,31)
    elif (month != 2):
      day = random.randint(1,30)
    elif(isLeapYear(year)):
      day = random.randint(1,29)
    else:
      day = random.randint(1,28)
  else:
    if(month % 2 == 0):
      day = random.randint(1,31)
    else:
      day = random.randint(1,30)

  return str(day) + "/" + str(month) + "/" + str(year)

def isLeapYear(year):
  if (year % 4 == 0 and (year % 100 != 0 or year % 400 == 0)):
    return True
  return False

for j in range(10):
    x = ""
    y = ""
    for i in range(11):
        x += str(random.randint(0,9))
        if (i < 9):
            y += str(random.randint(0,9))

    name = nameRandom()
    city = nameRandom()
    date = dateRandom()

    print(x + ";" + y + ";" + name + ";" + date + ";" + city)