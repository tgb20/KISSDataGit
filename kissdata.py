import csv

nameOfFile = "2016data"

with open(nameOfFile + '.csv', 'wb') as csvfile:
    datawriter = csv.writer(csvfile, delimiter=',',
                            quotechar='|', quoting=csv.QUOTE_MINIMAL)
    datawriter.writerow(['id', 'sex', 'month', 'age', 'education'])

with open('data.DUSMCPUB') as f:
    id = 0
    for entry in f:
        # Died of Overdose
        cause = entry[145:149]
        if(cause == 'X40 ') or (cause == 'X41 ') or (cause == 'X42 ') or (cause == 'X43 ') or (cause == 'X44 '):
            # Location of Sex
            sex = entry[68]
            
            # Location of Death Month
            monthOfDeath = entry[64:66]
            
            # Location of Age
            age = entry[78:80]
            
            # Location of Education
            education = entry[62]

            # Format the education to be readable
            aEducation = ''
            if(education == '1'):
                aEducation = '< 8th grade'
            elif(education == '2'):
                aEducation = '9-12th no diploma'
            elif(education == '3'):
                aEducation = 'High School Graduate'
            elif(education == '4'):
                aEducation = 'College Credit'
            elif(education == '5'):
                aEducation = 'Associates'
            elif(education == '6'):
                aEducation = 'Bachelors'
            elif(education == '7'):
                aEducation = 'Masters'
            elif(education == '8'):
                aEducation = 'Doctorate'
            elif(education == '9'):
                aEducation = 'Unknown'
            
            # Format the age to be readable
            aAge = ''
            if(age == '01'):
                aAge = '<1'
            elif(age == '02'):
                aAge = '1-4'
            elif(age == '03'):
                aAge = '5-14'
            elif(age == '04'):
                aAge = '15-24'
            elif(age == '05'):
                aAge = '25-34'
            elif(age == '06'):
                aAge = '35-44'
            elif(age == '07'):
                aAge = '45-54'
            elif(age == '08'):
                aAge = '55-64'
            elif(age == '09'):
                aAge = '65-74'
            elif(age == '10'):
                aAge = '75-84'
            elif(aAge == '11'):
                aAge = '85+'
            elif(aAge == '12'):
                aAge = 'Unknown'


            with open(nameOfFile + '.csv', 'a') as csvfile:
                datawriter = csv.writer(csvfile, delimiter=',',
                                    quotechar='|', quoting=csv.QUOTE_MINIMAL)
                datawriter.writerow([id, sex, monthOfDeath, aAge, aEducation])

            id += 1
