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
    public class Order : INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;
        protected void OnPropertyChanged(string propertyName)
        {
            if (this.PropertyChanged != null)
            {
                this.PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
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

        [DataMember(Name = "strategy")]
        private string strategy;
        public string Strategy
        {
            get { return strategy; }
            set
            {
                strategy = value;
                OnPropertyChanged("Strategy");
            }
        }

        [DataMember(Name = "buySell")]
        private string buySell;
        public string BuySell
        {
            get { return buySell; }
            set
            {
                buySell = value;
                OnPropertyChanged("BuySell");
            }
        }

        [DataMember(Name = "price")]
        private double price;
        public double Price
        {
            get { return price; }
            set
            {
                price = value;
                OnPropertyChanged("Price");
            }
        }

        [DataMember(Name = "quantity")]
        private int quantity;
        public int Quantity
        {
            get { return quantity; }
            set
            {
                quantity = value;
                OnPropertyChanged("Quantity");
            }
        }

        [DataMember(Name = "timePurchased")] //DateTime
        private string timePurchased;
        public string TimePurchased
        {
            get { return timePurchased; }
            set
            {
                timePurchased = value;
                OnPropertyChanged("TimePurchased");
            }
        }

        [DataMember(Name = "approved")]
        private string approved;
        public string Approved
        {
            get { return approved; }
            set
            {
                approved = value;
                OnPropertyChanged("Approved");
            }
        }

    }
}
