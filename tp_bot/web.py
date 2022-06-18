import requests


class RapidApi:
    def __init__(self):
        self.key = 'vrnctf{ch4ng1ng_g1t_h1st0ry}'


    def get_quotes(self,origin_place,destination,date):
        url = f"https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsequotes/v1.0/RU/RUB/ru-RU/{origin_place}/{destination}/{date}"

        headers = {
            'x-rapidapi-key': self.key,
            'x-rapidapi-host': "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com"
        }

        response = requests.request("GET", url, headers=headers)
        print(response.json())
        return response.json()

