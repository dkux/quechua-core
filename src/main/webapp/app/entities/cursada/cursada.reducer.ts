import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICursada, defaultValue } from 'app/shared/model/cursada.model';

export const ACTION_TYPES = {
  FETCH_CURSADA_LIST: 'cursada/FETCH_CURSADA_LIST',
  FETCH_CURSADA: 'cursada/FETCH_CURSADA',
  CREATE_CURSADA: 'cursada/CREATE_CURSADA',
  UPDATE_CURSADA: 'cursada/UPDATE_CURSADA',
  DELETE_CURSADA: 'cursada/DELETE_CURSADA',
  RESET: 'cursada/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICursada>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type CursadaState = Readonly<typeof initialState>;

// Reducer

export default (state: CursadaState = initialState, action): CursadaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CURSADA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CURSADA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CURSADA):
    case REQUEST(ACTION_TYPES.UPDATE_CURSADA):
    case REQUEST(ACTION_TYPES.DELETE_CURSADA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CURSADA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CURSADA):
    case FAILURE(ACTION_TYPES.CREATE_CURSADA):
    case FAILURE(ACTION_TYPES.UPDATE_CURSADA):
    case FAILURE(ACTION_TYPES.DELETE_CURSADA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CURSADA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CURSADA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CURSADA):
    case SUCCESS(ACTION_TYPES.UPDATE_CURSADA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CURSADA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/cursadas';

// Actions

export const getEntities: ICrudGetAllAction<ICursada> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CURSADA_LIST,
  payload: axios.get<ICursada>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ICursada> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CURSADA,
    payload: axios.get<ICursada>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICursada> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CURSADA,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICursada> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CURSADA,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICursada> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CURSADA,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
