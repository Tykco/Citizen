using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace CitiZen_TradingApp
{
    [DataContract]
    public class Portfolio : INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;
        protected void OnPropertyChanged(string propertyName)
        {
            if (this.PropertyChanged != null)
            {
                this.PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
            }
        }

        [DataMember(Name = "username")]
        private string userName;
        public string UserName
        {
            get { return userName; }
            set
            {
                userName = value;
                OnPropertyChanged("UserName");
            }
        }

        [DataMember(Name = "portfolioId")]
        private int portfolioId;
        public int PortfolioId
        {
            get { return portfolioId; }
            set
            {
                portfolioId = value;
                OnPropertyChanged("PortfolioId");
            }
        }

        [DataMember(Name = "created")] //DateTime
        private string created;
        public string Created
        {
            get { return created; }
            set
            {
                created = value;
                OnPropertyChanged("Created");
            }
        }

        [DataMember(Name = "startBalance")]
        private double startBalance;
        public double StartBalance
        {
            get { return startBalance; }
            set
            {
                startBalance = value;
                OnPropertyChanged("StartBalance");
            }
        }

        [DataMember(Name = "pfProfitLoss")]
        private double profitLoss;
        public double ProfitLoss
        {
            get { return profitLoss; }
            set
            {
                profitLoss = value;
                OnPropertyChanged("ProfitLoss");
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

    }
}
