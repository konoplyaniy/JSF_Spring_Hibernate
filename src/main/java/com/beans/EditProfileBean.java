package com.beans;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;

import org.imgscalr.Scalr;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.entity.UserEntity;
import com.service.TicketService;
import com.service.OvertimeService;
import com.service.UpdateControlService;
import com.utils.SupportUtils;

@ManagedBean
@SessionScoped
public class EditProfileBean implements Serializable {

    private UserEntity userPopup = new UserEntity();
    private UserEntity userEntity;
    private UploadedFile file;
    private byte[] avatar;
    private String password;
    private FacesMessage message;
    private StreamedContent imagemEnviada = new DefaultStreamedContent();
    private String imagemUploadName;
    private boolean exibeBotao = false;
    private CroppedImage croppedImage;
    private String newImageName;
    private Integer width;

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @ManagedProperty(value = "#{userService}")
    com.service.UserService userService;

    @ManagedProperty(value = "#{overtimeService}")
    OvertimeService overtimeService;

    @ManagedProperty(value = "#{updateControlService}")
    UpdateControlService updateControlService;

    @ManagedProperty(value = "#{ticketService}")
    TicketService ticketService;

    @ManagedProperty(value = "#{loginBean}")
    LoginBean loginBean;

    @ManagedProperty(value = "#{mail}")
    private Mail mail;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public com.service.UserService getUserService() {
        return userService;
    }

    public void setUserService(com.service.UserService userService) {
        this.userService = userService;
    }

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public UserEntity getUserPopup() {
        return userPopup;
    }

    public void setUserPopup(UserEntity userPopup) {
        this.userPopup = userPopup;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public OvertimeService getOvertimeService() {
        return overtimeService;
    }

    public void setOvertimeService(OvertimeService overtimeService) {
        this.overtimeService = overtimeService;
    }

    public TicketService getTicketService() {
        return ticketService;
    }

    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public UpdateControlService getUpdateControlService() {
        return updateControlService;
    }

    public void setUpdateControlService(UpdateControlService updateControlService) {
        this.updateControlService = updateControlService;
    }

    public String getImagemUploadName() {
        return imagemUploadName;
    }

    public void setImagemUploadName(String imagemUploadName) {
        this.imagemUploadName = imagemUploadName;
    }

    public FacesMessage getMessage() {
        return message;
    }

    public void setMessage(FacesMessage message) {
        this.message = message;
    }

    public StreamedContent getImagemEnviada() {
        return imagemEnviada;
    }

    public void setImagemEnviada(StreamedContent imagemEnviada) {
        this.imagemEnviada = imagemEnviada;
    }

    public String getImagemTemporaria() {
        return imagemUploadName;
    }

    public void setImagemTemporaria(String imagemTemporaria) {
        this.imagemUploadName = imagemTemporaria;
    }

    public boolean isExibeBotao() {
        return exibeBotao;
    }

    public void setExibeBotao(boolean exibeBotao) {
        this.exibeBotao = exibeBotao;
    }

    public CroppedImage getCroppedImage() {
        return croppedImage;
    }

    public void setCroppedImage(CroppedImage croppedImage) {
        this.croppedImage = croppedImage;
    }

    public String getNewImageName() {
        return newImageName;
    }

    public void setNewImageName(String newImageName) {
        this.newImageName = newImageName;
    }

    /**
     * Method for upload avatar to data base
     *
     * @param event - upload event
     */
    public void handleFileUpload(FileUploadEvent event) {
        // file = event.getFile();
        try {
            // avatar = IOUtils.toByteArray(event.getFile().getInputstream());
            // message = new FacesMessage("Succesful upload", file.getFileName()
            // + "
            // is uploaded.");
            // FacesContext.getCurrentInstance().addMessage(null, message);
            // } catch (IOException e) {
            // message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not
            // upload",
            // "");
            // }
            // FacesContext.getCurrentInstance().addMessage(null, message);
            imagemUploadName = event.getFile().getFileName();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
            String pathToImage = scontext.getRealPath("") + File.separator + "images" + File.separator
                    + imagemUploadName + ".jpg";
            try (InputStream is = event.getFile().getInputstream();
                 OutputStream out = new FileOutputStream(pathToImage)) {

                BufferedImage img = ImageIO.read(is);
                BufferedImage scaledImg;

                scaledImg = Scalr.resize(img, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_WIDTH, 320, 320);

                if (scaledImg.getWidth() > scaledImg.getHeight()) {
                    width = scaledImg.getWidth();
                } else {
                    width = scaledImg.getHeight();
                }
                ImageIO.write(scaledImg, "jpg", out);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void createImage(byte[] bytes, String pathToImage) {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(pathToImage);
            fos.write(bytes);
            fos.close();
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        }
    }

    /**
     * Method for update User
     *
     * @param user - UserEntity
     */
    //TODO inspect and rename
    public void updadteUser(UserEntity user) {
        boolean isPasswordChanged = false;
        if (croppedImage != null) {
            setNewImageName(getRandomImageName());
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String newFileName = externalContext.getRealPath("") + File.separator + "images" + File.separator
                    + getNewImageName();
            FileInputStream fileInputStream = null;
            FileImageOutputStream imageOutput;
            try {
                imageOutput = new FileImageOutputStream(new File(newFileName));
                imageOutput.write(croppedImage.getBytes(), 0, croppedImage.getBytes().length);
                imageOutput.close();
                avatar = new byte[(int) new File(newFileName).length()];
                fileInputStream = new FileInputStream(new File(newFileName));
                fileInputStream.read(avatar);
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cropping failed."));
                return;
            }
            try {
                fileInputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Cropping finished."));
        }
        if (!password.equals("")) {
            isPasswordChanged = true;
            user.setPassword(SupportUtils.MD5(password));
        }
        if (avatar != null) {
            user.setAvatar(avatar);
        }
        userService.updateUser(user);
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "User updated",
                user.getFirst_name() + " " + user.getLast_name());
        if (isPasswordChanged) {
            mail.sendThreadEmail("Dreamscape.QA notification",
                    "Hello " + user.getFirst_name() + " " + user.getLast_name() + "!" +
                            "\nYour password was changed for Dreamscape QA portal\n" +
                            "Login: " + user.getLogin() +
                            "\nYour new Password: " + password,
                    user.getEmail());
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        imagemUploadName = "";
    }

    /**
     * Method for delete user from data base
     *
     * @param user - UserEntity
     */
    public void deleteUser(UserEntity user) {
        Integer adminId = 1;
        for (UserEntity users : userService.getAllUsers()) {
            if (users.getRole().equals("admin")) {
                adminId = users.getId();
                break;
            }
        }
        if (user.getRole().equals("admin")) {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Admin can't be deleted", "");
        } else {
            //TODO inspect, possibly will be deleted
            /*for (OvertimeEntity overtime : overtimeService.getOvertimesByMonth(new Date())) {
				if (user.getId() == overtime.getUser_id()) {
					overtimeService.deleteteOvertime(overtime);
				}
			}
			for (TicketEntity ticket : ticketService.getAllTickets()) {
				if (user.getId() == ticket.getId()) {
					ticket.setId(adminId);
					ticketService.updateTicket(ticket);
				}
			}
			for (SolvedTicketEntity solved : solvedTicketService.getAllSolvedTickets()) {
				if (user.getUser_id() == solved.getUser_id()) {
					solved.setUser_id(adminId);
					solvedTicketService.update(solved);
				}
			}

			for (UpdateControlEntity update : updateControlService.getAllUpdateControl()) {
				if (user.getUser_id() == update.getUser_id()) {
					update.setUser_id(adminId);
					updateControlService.update(update);
				}
			}*/
            userService.deleteUser(user);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "User deleted",
                    user.getFirst_name() + " " + user.getLast_name());
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onRowSelect(SelectEvent event) {
        userPopup = (UserEntity) event.getObject();
    }

    public void buttonAction(ActionEvent actionEvent) {
        userPopup = loginBean.getUser();
    }

    private String getRandomImageName() {
        int i = (int) (Math.random() * 100000);

        return String.valueOf(i);
    }

}
