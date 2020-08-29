import subprocess
import os
import time

while True:
        res = subprocess.Popen("ps -ef | grep java",stdout=subprocess.PIPE,shell=True)
        python_process =res.stdout.readlines()

        isSurvival = False
        for process in python_process:
            if "amplee" in str(process, encoding="utf8"):
                isSurvival = True
                break

        if not isSurvival:
            print("需要重启")
            os.system('nohup java -jar -Duser.timezone=GMT+08 amplee.jar --spring.profiles.active=pro &')

        time.sleep(10)