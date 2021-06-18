import classes
import web
import datetime

class Utils:

    db = classes.DB()
    tg_db=classes.Telegram_DB()
    api = web.RapidApi()

    def get_message(self):
        list = self.db.get_favorites()
        result = []
        for item in list:
            res = self.api.get_quotes(item[1], item[2], item[3].strftime("%Y-%m-%d"))
            count = 0
            for i in res['Quotes']:
                if i['MinPrice'] >= item[-1]:
                    continue
                else:
                    count += 1
            if count == 0:
                continue
            result_str = 'На избранные направления '
            for i in res['Quotes']:
                if i['MinPrice'] >= item[-1]:
                    continue
                origin = ''

                destination = ''
                date = datetime.datetime.strptime(i['OutboundLeg']['DepartureDate'].split('T')[0], '%Y-%m-%d').strftime(
                    "%d.%m.%Y")
                for j in res['Places']:
                    if j['PlaceId'] == i['OutboundLeg']["OriginId"]:
                        origin = j['Name']
                    elif j['PlaceId'] == i['OutboundLeg']["DestinationId"]:
                        destination = j['Name']
                result_str = result_str + f'{origin} - {destination} на {date}\n'
            result_str = result_str + f'появились новые цены.'
            result.append((self.tg_db.get_person_by_id(item[0]), result_str))
        return result