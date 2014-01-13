/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.mi.mcloud.selfcare.view;

import com.vaadin.ui.Component;

/**
 *
 * @author bor
 */
class ValidComponent {

        private Component component;
        private Boolean valid = Boolean.FALSE;

        public ValidComponent(Component component, Boolean valid) {
            this.component = component;
            this.valid = valid;
        }

        public Component getComponent() {
            return component;
        }

        public void setComponent(Component component) {
            this.component = component;
        }

        public Boolean isValid() {
            return valid;
        }

        public void setValid(Boolean valid) {
            this.valid = valid;
        }
    }
