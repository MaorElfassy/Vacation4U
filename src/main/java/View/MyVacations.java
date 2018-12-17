package View;

import Controller.Controller;
import Model.Excpetions.V4UException;
import Model.Vacation;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class MyVacations {
    public Vacation vacation;
    public Button details;
    public Button edit;
    public Button delete;
    public String from;
    public String destination;
    public String SellerUserName;
    public LocalDate departureDate;


   // public static String get_static_vacationID(){
        //return vacationID;
   // }
    public Vacation getVacation() {
        return vacation;
    }

    public void setVacation(Vacation vacation) {
        this.vacation = vacation;
    }

    public Button getDetails() {
        return details;
    }

    public void setDetails(Button details) {
        this.details = details;
    }

    public Button getEdit() {
        return edit;
    }

    public void setEdit(Button edit) {
        this.edit = edit;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete= delete;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDate returnDate;

    public String getSellerUserName() {
        return SellerUserName;
    }

    public void setSellerUserName(String sellerUserName) {
        SellerUserName = sellerUserName;
    }


    public MyVacations(Vacation vacation, Button details, Button edit,Button delete) {
        this.vacation = vacation;
        this.departureDate = LocalDate.parse(vacation.getDepartureDate());
        this.returnDate = LocalDate.parse(vacation.getReturnDate());
        this.from = vacation.getFrom();
        this.destination = vacation.getDestination();
        this.details = details;
        this.edit = edit;
        this.delete=delete;

        edit.setText("Edit");
        delete.setText("Delete");
        details.setText("Details");
//        buy.maxWidth(Double.MAX_VALUE);
//        buy.maxHeight(Double.MAX_VALUE);


        details.setOnAction(event -> {

            Stage s = (Stage) details.getScene().getWindow();
//        s.close();
            try {
                Controller.vacationID = vacation.getVacationID();
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("VacationDetailsWindow.fxml"));
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(true);
                stage.setTitle("Details Vacation");
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Controller controller = Controller.getInstance();


        // TODO: 17/12/2018 delete function  Itzik
        delete.setOnAction(event -> {
            controller.delete_myVacation();
        });

        // TODO: 17/12/2018 edit function  Itzik
        edit.setOnAction(event -> {
            boolean connected_user  = true;
            if (controller.get_connected_user_id()==null)
                connected_user=false;
            if (connected_user) {
                boolean flag = false;
                if (controller.get_connected_user_id().equals(this.SellerUserName)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Your request to buy has been cancelled");
                    alert.setHeaderText("You can't buy vacation from yourself");
                    alert.showAndWait();
                    return;
                }
                try {
                    flag = controller.insertBuyingRequest(vacation.getVacationID(),vacation.getUserName());
                } catch (V4UException e) {
                    System.out.println("error in insert");
                }
                if (flag) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("BuyingRequest Sent");
                    alert.setHeaderText("Your request to buy has been sent to Buyer. \nPlease check your requests page soon");
                    alert.showAndWait();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Why Buying again?!");
                    alert.setHeaderText("You already request to buy this vacation. \nPlease keep calm and check your requests page soon");
                    alert.showAndWait();

                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Log in");
                alert.setHeaderText("Only signed users are allowing to request to buy vacations\nPlease close the window, log in and try again");
                alert.showAndWait();
            }

        });

    }


}