using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.ComponentModel;
using System.Runtime.Serialization;

namespace CitiZen_TradingApp
{
    [DataContract]
    public class Position : INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;
        protected void OnPropertyChanged(string propertyName)
        {
            if (this.PropertyChanged != null)
            {
                this.PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
            }
        }

        [DataMember(Name = "companyName")]
        private string companyName;
        public string CompanyName
        {
            get { return companyName; }
            set
            {
                companyName = value;
                OnPropertyChanged("CompanyName");
            }
        }

        [DataMember(Name = "ticker")]
        private string ticker;
        public string Ticker
        {
            get { return ticker; }
            set
            {
                ticker = value;
                OnPropertyChanged("Ticker");
            }
        }

        [DataMember(Name = "sharesProfitLoss")]
        private double sharesProfitLoss;
        public double SharesProfitLoss
        {
            get { return sharesProfitLoss; }
            set
            {
                sharesProfitLoss = value;
                OnPropertyChanged("SharesProfitLoss");
            }
        }

        [DataMember(Name = "shares")]
        private int shares;
        public int Shares
        {
            get { return shares; }
            set
            {
                shares = value;
                OnPropertyChanged("Shares");
            }
        }

        [DataMember(Name = "user")]
        private User user;
        public User User
        {
            get { return user; }
            set
            {
                user = value;
                OnPropertyChanged("User");
            }
        }

        private int liveId;
        public int LiveId
        {
            get { return liveId; }
            set
            {
                liveId = value;
                OnPropertyChanged("LiveId");
            }
        }

    }
}
